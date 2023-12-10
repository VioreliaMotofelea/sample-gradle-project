package org.example;

import org.apache.commons.math3.util.Pair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionManager {

    private static ConnectionManager instance = null;

    private Map<PeerAddressInfo, Peer> peersMap;
    private Map<Pair<Peer, Lock>, MessageQ> connections;

    private PrintWriter notifierOut;
    private BufferedReader notifierIn;
    private PipedInputStream pipedInputStream;
    private PipedOutputStream pipedOutputStream;

    private PeerAddressInfo currentPeer;

    private ExecutorService serverExecutorService;

    private ConnectionManager() {
        peersMap = new HashMap<>();
        connections = new HashMap<>();
        serverExecutorService = Executors.newFixedThreadPool(3);
        currentPeer = null;

        pipedInputStream = new PipedInputStream();
        pipedOutputStream = new PipedOutputStream();

        try {
            pipedInputStream.connect(pipedOutputStream);
            this.notifierIn = new BufferedReader(new InputStreamReader(pipedInputStream));
            this.notifierOut = new PrintWriter(new OutputStreamWriter(pipedOutputStream), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Starts the server on the given port
     * @param port
     */

    public void startServer(int port) {
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(port);

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    serverExecutorService.submit(() -> handleNewConnection(clientSocket));
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error when starting server: " + e.getMessage());
            }
        }).start();
    }

    /**
     * Connects to a server as a client
     * @param serverAddr
     * @param serverPort
     */

    void connectToServerAsClient(String serverAddr, int serverPort) {
        try {
            Socket clientSocket = new Socket(serverAddr, serverPort);
            new Thread(() -> handleNewConnection(clientSocket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles a new connection
     * @param clientSocket
     */

    private void handleNewConnection(Socket clientSocket) {
        try {
            Peer peer = new Peer(
                    clientSocket.getInetAddress(),
                    clientSocket.getPort(),
                    clientSocket.getInputStream(),
                    clientSocket.getOutputStream()
            );
            this.addConnection(peer);

            Thread readThread = new Thread(() -> readMessages(peer));
            Thread writeThread = new Thread(() -> writeMessages(peer));

            readThread.start();
            writeThread.start();

            readThread.join();
            writeThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void readMessages(Peer peer) {
        BufferedReader in = new BufferedReader(new InputStreamReader(peer.getInputStream()));
        try {
            while (true) {
                String message = in.readLine();
                if (message == null) {
                    break;
                }

                this.addMessage(peer, new Message(MessageType.MESSAGE, "Received: " + message));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeMessages(Peer peer) {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(peer.getOutputStream()), true);
        Scanner scanner = new Scanner(System.in);

        Lock lock = null;
        for (Pair<Peer, Lock> pair : connections.keySet()) {
            Peer peer1 = pair.getFirst();
            if (peer1.getPort() == peer.getPort()) {
                lock = pair.getSecond();
                break;
            }
        }

        if (lock == null)
            return;

        lock.lock();

        try {
            while (peer.getInputStream() != null) {
                lock.lock();
                String message = scanner.nextLine();
                if (message == null)
                    continue;

                out.println(message);
                if (message.contains("%disconnect"))
                    break;

                addMessage(peer, new Message(MessageType.MESSAGE, "Sent: " + message));

                if (!message.contains("%exit"))
                    lock.unlock();
            }
        } catch (Exception e) {
            if (e.getMessage().equals("Socket closed"))
                System.out.println("Disconnected from peer chatroom!");
            else
                e.printStackTrace();
        } finally {
            out.close();
            lock.unlock();
            System.out.println("Write closed");
        }
    }


    public void connectToChatroom(String serverAddr, int serverPort) {
        PeerAddressInfo peerAddressInfo = new PeerAddressInfo(
                new InetSocketAddress(serverAddr, serverPort).getAddress(),
                serverPort
        );
        currentPeer = peerAddressInfo;

        try {
            for (Pair<Peer, Lock> pair : connections.keySet()) {
                Peer peer = pair.getFirst();

                if (peer.getPort() == serverPort) {

                    System.out.println("Connected to peer chatroom!");

                    MessageQ messages = this.getMessages(peer);
                    messages.setMqForSendingMessages();
                    messages.sendOldMessagesThroughPrintWriter();

                    pair.getSecond().unlock();

                    BufferedReader in = messages.getBufferedReader();
                    Thread read = new Thread(() -> readMessagesChatroom(in));
                    read.start();
                    read.join();
                    messages.closePipe();

                    pair.getSecond().lock();

                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readMessagesChatroom(BufferedReader in) {
        try {
            while (true) {
                String message = in.readLine();
                if (message == null || message.contains("%exit")) {
                    break;
                }
                if (message.startsWith("Sent: "))
                    continue;

                System.out.println(message);
            }
        } catch (Exception e) {
            if (e.getMessage().equals("Stream closed"))
                System.out.println("Disconnected from peer chatroom!");
            else if (e.getMessage().equals("Pipe broken"))
                System.out.println("Disconnected from peer chatroom bc f broken pipe!");
            else
                e.printStackTrace();
        }
    }



    public synchronized void addConnection(Peer peer) {
        connections.put(new Pair<>(peer, new ReentrantLock()), new MessageQ());
        PeerAddressInfo peerAddressInfo = new PeerAddressInfo(peer.getAddress(), peer.getPort());
        peersMap.put(peerAddressInfo, peer);
    }

    public List<String> getConnectedPeersNames() {
        List<String> connectedPeersNames = new ArrayList<>();
        for (Pair<Peer, Lock> pair : connections.keySet()) {
            Peer peer = pair.getFirst();
            connectedPeersNames.add(peer.getPort() + " " + peer.getAddress().getHostAddress());
        }
        return connectedPeersNames;
    }

    public synchronized void addMessage(Peer peer, Message message) {
        for (Pair<Peer, Lock> pair : connections.keySet()) {
            Peer peer1 = pair.getFirst();
            if (peer1.getPort() == peer.getPort()) {
                MessageQ messages = connections.get(pair);
                messages.addMessage(message);
                break;
            }
        }
    }

    public synchronized MessageQ getMessages(Peer peer) {
        for (Pair<Peer, Lock> pair : connections.keySet()) {
            Peer peer1 = pair.getFirst();
            if (peer1.getPort() == peer.getPort()) {
                MessageQ messages = connections.get(pair);
                return messages;
            }
        }

        return null;
    }


    public static ConnectionManager getInstance() {
        if (instance == null)
            instance = new ConnectionManager();
        return instance;
    }
}
