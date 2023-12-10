package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionManager {

    private static ConnectionManager instance = null;

    private Map<PeerAddressInfo, Peer> peersMap;
    private Map<Peer, MessageQ> connections;

    private PrintWriter messagingOut;

    private ExecutorService serverExecutorService;

    private ConnectionManager() {
        peersMap = new HashMap<>();
        connections = new HashMap<>();
        serverExecutorService = Executors.newFixedThreadPool(3);
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

            Thread readThread = new Thread(() -> readMessagesForLifetime(peer));

            readThread.start();
            readThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void readMessagesForLifetime(Peer peer) {
        BufferedReader in = new BufferedReader(new InputStreamReader(peer.getInputStream()));
        try {
            while (true) {
                String message = in.readLine();
                if (message == null) {
                    break;
                }

//                if (message.equals("%exit")) {
//                    this.addMessage(peer, new Message(MessageType.MESSAGE, "Peer disconnected!"));
//                    continue;
//                }
                this.addMessage(peer, new Message(MessageType.MESSAGE, "Received: " + message));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                peer.getInputStream().close();
                peer.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void connectToChatroom(String serverAddr, int serverPort) {
        try {
//            PeerAddressInfo info = new PeerAddressInfo(
//                    new InetSocketAddress(serverAddr, serverPort).getAddress(),
//                    serverPort
//            );
//            Peer peer = peersMap.get(info);

            for (Peer peer : connections.keySet()) {
                if (peer.getPort() == serverPort) {

                    System.out.println("Connected to peer chatroom!");

                    MessageQ messages = this.getMessages(peer);
                    messages.setMqForSendingMessages();
                    messages.sendOldMessagesThroughPrintWriter();

                    BufferedReader in = messages.getBufferedReader();
                    messagingOut = new PrintWriter(peer.getOutputStream(), true);

                    Thread read = new Thread(() -> readMessagesChatroom(in));
                    Thread write = new Thread(() -> writeMessagesChatroom(peer));

                    write.start();
                    read.start();

                    read.join();
                    write.join();

                    messages.closePipe();

                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void writeMessagesChatroom(Peer peer) {
        Scanner scanner = new Scanner(System.in);

        try {
            while (true) {
                String message = scanner.nextLine();
                messagingOut.println(message);

                if (message.contains("%exit"))
                    break;

                addMessage(peer, new Message(MessageType.MESSAGE, "Sent: " + message));
            }
        } catch (Exception e) {
            if (e.getMessage().equals("Socket closed"))
                System.out.println("Disconnected from peer chatroom!");
            else
                e.printStackTrace();
        } finally {
            messagingOut = null;
        }
    }

    public void readMessagesChatroom(BufferedReader in) {
        try {
            while (messagingOut != null) {
                String message = in.readLine();
                if (message == null) {
                    break;
                }
                if (message.startsWith("Sent: ") || message.contains("%exit"))
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

        } finally {
            in = null;
        }
    }

    public synchronized void addConnection(Peer peer) {
        connections.put(peer, new MessageQ());
        PeerAddressInfo peerAddressInfo = new PeerAddressInfo(peer.getAddress(), peer.getPort());
        peersMap.put(peerAddressInfo, peer);
    }

    public List<String> getConnectedPeersNames() {
        List<String> connectedPeersNames = new ArrayList<>();
        for (Peer peer : connections.keySet())
            connectedPeersNames.add(peer.getPort() + " " + peer.getAddress().getHostAddress());
        return connectedPeersNames;
    }

    public synchronized void addMessage(Peer peer, Message message) {
        connections.get(peer).addMessage(message);
    }

    public synchronized MessageQ getMessages(Peer peer) {
        return connections.get(peer);
    }


    public static ConnectionManager getInstance() {
        if (instance == null)
            instance = new ConnectionManager();
        return instance;
    }
}
