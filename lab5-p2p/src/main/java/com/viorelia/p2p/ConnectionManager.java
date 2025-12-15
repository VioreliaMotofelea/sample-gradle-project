package com.viorelia.p2p;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionManager {

    private final PeerNode.Config cfg;

    private final ConcurrentMap<String, PeerConnection> peersByName = new ConcurrentHashMap<>();

    private final ExecutorService acceptPool = Executors.newSingleThreadExecutor();
    private final ExecutorService ioPool = Executors.newFixedThreadPool(50);
    
    // ordered locks to prevent circular wait
    private final ReentrantLock connectionLock = new ReentrantLock(true);

    private volatile boolean running = true;
    private ServerSocket serverSocket;

    public ConnectionManager(PeerNode.Config cfg) {
        this.cfg = cfg;
    }

    public void startServer() throws Exception {
        serverSocket = new ServerSocket(cfg.port());
        acceptPool.submit(() -> {
            System.out.println("[server] listening on " + cfg.port());
            while (running) {
                try {
                    Socket s = serverSocket.accept();
                    PeerConnection conn = PeerConnection.incoming(cfg, s, this, ioPool);
                    conn.start();
                } catch (IOException e) {
                    if (running) System.out.println("[server] accept error: " + e.getMessage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void connect(String host, int port) {
        System.out.println("[connect] attempting to connect to " + host + ":" + port + " (this may take a few seconds)");
        System.out.flush();
        ioPool.submit(() -> {
            try {
                Socket s = new Socket();
                s.connect(new java.net.InetSocketAddress(host, port), 5000); // 5 second timeout
                System.out.println("[connect] socket connected, starting handshake...");
                System.out.flush();
                PeerConnection conn = PeerConnection.outgoing(cfg, s, this, ioPool);
                conn.start();
                System.out.println("[connect] connection established to " + host + ":" + port);
                System.out.flush();
            } catch (java.net.SocketTimeoutException e) {
                System.out.println("[connect] timeout: could not connect to " + host + ":" + port + " within 5 seconds");
                System.out.println("[connect] make sure the peer is running and listening on port " + port);
                System.out.flush();
            } catch (java.net.ConnectException e) {
                System.out.println("[connect] connection refused: " + e.getMessage());
                System.out.println("[connect] make sure the peer is running and listening on port " + port);
                System.out.flush();
            } catch (IOException e) {
                System.out.println("[connect] failed: " + e.getMessage());
                System.out.flush();
            } catch (Exception e) {
                System.out.println("[connect] unexpected error: " + e.getMessage());
                e.printStackTrace();
                System.out.flush();
            }
        });
        System.out.println("[connect] connection attempt submitted (non-blocking)");
        System.out.flush();
    }

    void registerPeer(String peerName, PeerConnection conn) {
        try {
            if (connectionLock.tryLock(1, TimeUnit.SECONDS)) {
                try {
                    PeerConnection old = peersByName.put(peerName, conn);
                    if (old != null && old != conn) {
                        System.out.println("[mgr] replacing old connection for " + peerName);
                        old.close("replaced");
                    }
                    System.out.println("[mgr] peer registered: " + peerName);
                    System.out.flush();
                } finally {
                    connectionLock.unlock();
                }
            } else {
                System.out.println("[mgr] warning: could not acquire lock for registration, potential deadlock");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[mgr] registration interrupted");
        }
    }

    void unregisterPeer(String peerName, PeerConnection conn) {
        try {
            if (connectionLock.tryLock(1, TimeUnit.SECONDS)) {
                try {
                    peersByName.remove(peerName, conn);
                    System.out.println("[mgr] peer removed: " + peerName);
                } finally {
                    connectionLock.unlock();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void sendChat(String peerName, String text) throws Exception {
        PeerConnection conn = peersByName.get(peerName);
        if (conn == null) {
            System.out.println("[send] no such peer connected: " + peerName);
            System.out.println("[send] available peers: " + peersByName.keySet());
            return;
        }
        conn.send(Message.chat(cfg.name(), text, cfg.json()));
    }

    public void closePeer(String peerName) throws Exception {
        PeerConnection conn = peersByName.get(peerName);
        if (conn != null) {
            conn.send(Message.bye(cfg.name(), cfg.json()));
            try {
                Thread.sleep(50);
            } catch (InterruptedException ignored) {}
            conn.close("!bye by user");
        }
    }

    public void shutdown() throws Exception {
        running = false;
        System.out.println("[mgr] shutting down...");

        for (PeerConnection c : peersByName.values()) {
            c.send(Message.bye(cfg.name(), cfg.json()));
        }
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {}
        
        try { if (serverSocket != null) serverSocket.close(); } catch (IOException ignored) {}
        for (PeerConnection c : peersByName.values()) c.close("shutdown");

        acceptPool.shutdown();
        ioPool.shutdown();
        try {
            if (!acceptPool.awaitTermination(2, java.util.concurrent.TimeUnit.SECONDS)) {
                acceptPool.shutdownNow();
            }
            if (!ioPool.awaitTermination(2, java.util.concurrent.TimeUnit.SECONDS)) {
                ioPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            acceptPool.shutdownNow();
            ioPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
