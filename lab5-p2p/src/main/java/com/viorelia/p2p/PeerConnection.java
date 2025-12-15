package com.viorelia.p2p;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class PeerConnection {

    private final PeerNode.Config cfg;
    private final Socket socket;
    private final ConnectionManager mgr;
    private final ExecutorService ioPool;

    private final BlockingQueue<String> outbound = new LinkedBlockingQueue<>();
    private volatile boolean closed = false;

    private volatile String peerName;
    private final boolean outgoing;

    private PeerConnection(PeerNode.Config cfg, Socket socket, ConnectionManager mgr, ExecutorService ioPool, boolean outgoing) {
        this.cfg = cfg;
        this.socket = socket;
        this.mgr = mgr;
        this.ioPool = ioPool;
        this.outgoing = outgoing;
    }

    public static PeerConnection incoming(PeerNode.Config cfg, Socket s, ConnectionManager mgr, ExecutorService ioPool) {
        return new PeerConnection(cfg, s, mgr, ioPool, false);
    }

    public static PeerConnection outgoing(PeerNode.Config cfg, Socket s, ConnectionManager mgr, ExecutorService ioPool) {
        return new PeerConnection(cfg, s, mgr, ioPool, true);
    }

    public void start() throws Exception {
        System.out.println(tag() + "connected from/to " + socket.getRemoteSocketAddress());
        System.out.flush();

        ioPool.submit(this::senderLoop);
        ioPool.submit(this::readerLoop);

        // Outgoing side: immediately send hello and require ack first.
        if (outgoing) {
            System.out.println(tag() + "sending HELLO to initiate handshake");
            System.out.flush();
            send(Message.hello(cfg.name(), cfg.json()));
        }
    }

    public void send(Message msg) throws Exception {
        if (closed) return;
        // Use offer() to avoid blocking - prevents deadlock if queue is full
        // This is a non-blocking operation, preventing deadlock scenarios
        boolean added = outbound.offer(msg.encode(cfg.json()));
        if (!added && !closed) {
            System.out.println(tag() + "warning: message queue full, dropping message");
        }
    }

    private void senderLoop() {
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
            while (!closed) {
                String line = outbound.take();
                if (line.isEmpty() && closed) {
                    // Poison pill to unblock and exit
                    break;
                }
                if (!closed) {
                    System.out.println(tag() + "sending: " + line);
                    System.out.flush();
                    out.write(line);
                    out.newLine();
                    out.flush();
                }
            }
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            if (!closed) {
                System.out.println(tag() + "sender error: " + e.getMessage());
                System.out.flush();
            }
        } finally {
            close("senderLoop end");
        }
    }

    private void readerLoop() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {

            // HANDSHAKE RULE:
            // - Incoming connection: first line must be HELLO, else close.
            // - Outgoing connection: first line we expect is ACK, else close.

            String first = in.readLine();
            if (first == null) {
                close("no first line");
                return;
            }

            Message m1 = Message.decode(first, cfg.json());
            System.out.println(tag() + "received first message: type=" + m1.type() + " from=" + m1.from());
            System.out.flush();

            if (!outgoing) {
                // incoming: must be hello
                if (m1.type() != Message.Type.HELLO || m1.from() == null || m1.from().isBlank()) {
                    System.out.println(tag() + "ERROR: expected HELLO but got " + m1.type());
                    System.out.flush();
                    close("incoming without hello");
                    return;
                }
                peerName = m1.from();
                System.out.println(tag() + "registering peer: " + peerName);
                System.out.flush();
                mgr.registerPeer(peerName, this);
                System.out.println(tag() + "sending ACK and HELLO");
                System.out.flush();
                send(Message.ack(cfg.name(), cfg.json()));
                // Send our own HELLO so the outgoing side knows our name
                send(Message.hello(cfg.name(), cfg.json()));
            } else {
                // outgoing: must be ack
                if (m1.type() != Message.Type.ACK) {
                    System.out.println(tag() + "ERROR: expected ACK but got " + m1.type());
                    System.out.flush();
                    close("outgoing without ack");
                    return;
                }
                System.out.println(tag() + "received ACK, waiting for HELLO");
                System.out.flush();
                // We'll learn the peer's name when they send their HELLO
            }

            // Main loop
            String line;
            while ((line = in.readLine()) != null && !closed) {
                try {
                    Message msg = Message.decode(line, cfg.json());

                    switch (msg.type()) {
                        case HELLO -> {
                            // allow hello later too; update peer name
                            if (msg.from() != null && !msg.from().isBlank()) {
                                String newPeerName = msg.from();
                                boolean wasNew = (peerName == null || !peerName.equals(newPeerName));
                                if (wasNew) {
                                    System.out.println(tag() + "received HELLO from " + newPeerName);
                                    System.out.flush();
                                    peerName = newPeerName;
                                    mgr.registerPeer(peerName, this);
                                    // If this is an outgoing connection receiving HELLO after ACK,
                                    // send ACK back to complete the handshake
                                    if (outgoing) {
                                        System.out.println(tag() + "sending ACK to complete handshake");
                                        System.out.flush();
                                        send(Message.ack(cfg.name(), cfg.json()));
                                    }
                                }
                            }
                        }
                        case BYE -> {
                            close("peer said bye");
                            return;
                        }
                        case CHAT -> {
                            String who = peerName != null ? peerName : socket.getRemoteSocketAddress().toString();
                            System.out.println("[" + who + "] " + msg.text());
                            System.out.flush();
                        }
                        case ACK -> {
                            // ack after hello; ignore
                        }
                    }
                } catch (Exception e) {
                    if (!closed) {
                        System.out.println(tag() + "message decode error: " + e.getMessage());
                    }
                }
            }

        } catch (IOException e) {
            if (!closed) {
                System.out.println(tag() + "reader error: " + e.getMessage());
                System.out.flush();
            }
        } catch (Exception e) {
            if (!closed) {
                System.out.println(tag() + "unexpected error: " + e.getMessage());
                e.printStackTrace();
                System.out.flush();
            }
        } finally {
            close("readerLoop end");
        }
    }

    public void close(String reason) {
        if (closed) return;
        synchronized (this) {
            if (closed) return;
            closed = true;
        }

        if (peerName != null) mgr.unregisterPeer(peerName, this);

        // Unblock sender thread with poison pill
        outbound.offer(""); // unblock sender if waiting on take()
        
        try { 
            socket.close(); 
        } catch (IOException ignored) {}

        System.out.println(tag() + "closed: " + reason);
        System.out.flush();
    }

    private String tag() {
        return outgoing ? "[out] " : "[in ] ";
    }
}
