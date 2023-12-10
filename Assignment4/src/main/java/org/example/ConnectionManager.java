package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

            Thread readThread = new Thread(() -> readMessages(peer));

            readThread.start();
            readThread.join();
        } catch (IOException | InterruptedException e) {
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
        } catch (IOException e) {
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
                    MessageQ messages = this.getMessages(peer);
                    for (Message message : messages.getMessages())
                        System.out.println(message.getMessage());

                    Thread t = new Thread(() -> writeMessages(peer));
                    t.start();
                    t.join();

                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void writeMessages(Peer peer) {
        messagingOut = new PrintWriter(peer.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Connected to peer chatroom!");

        try {
            while (true) {
                String message = scanner.nextLine();
                messagingOut.println(message);

                if (message.equals("%exit"))
                    break;

                this.addMessage(peer, new Message(MessageType.MESSAGE, "Sent: " + message));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                messagingOut.close();
//                peer.getOutputStream().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
