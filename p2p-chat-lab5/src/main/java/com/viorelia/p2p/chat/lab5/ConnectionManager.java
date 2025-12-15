package com.viorelia.p2p.chat.lab5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Connection Manager thread that handles incoming connections and manages all peer connections.
 * Implements proper concurrency handling and thread pool management.
 */
public class ConnectionManager {
    private final int listenPort;
    private final MessageFormat format;
    private final String myName;
    private final Map<String, PeerConnection> connections = new ConcurrentHashMap<>();
    private final ExecutorService executorService;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private ServerSocket serverSocket;
    private Thread managerThread;
    private final ConnectionEventHandler eventHandler;

    public interface ConnectionEventHandler {
        void onConnectionEstablished(PeerConnection connection);
        void onConnectionClosed(PeerConnection connection);
        void onMessageReceived(PeerConnection connection, String message);
        void onProtocolError(String error);
    }

    public ConnectionManager(int listenPort, MessageFormat format, String myName, 
                           ConnectionEventHandler eventHandler) {
        this.listenPort = listenPort;
        this.format = format;
        this.myName = myName;
        this.eventHandler = eventHandler;
        this.executorService = Executors.newCachedThreadPool();
    }

    public void start() throws IOException {
        if (running.get()) {
            return;
        }

        serverSocket = new ServerSocket(listenPort);
        running.set(true);

        // Connection manager thread
        managerThread = new Thread(() -> {
            System.out.println("[ConnectionManager] Listening on port " + listenPort);
            
            while (running.get()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("[ConnectionManager] Incoming connection from: " 
                        + clientSocket.getRemoteSocketAddress());
                    
                    // Handle new connection in a separate thread
                    executorService.submit(() -> handleIncomingConnection(clientSocket));
                } catch (IOException e) {
                    if (running.get()) {
                        System.err.println("[ConnectionManager] Error accepting connection: " + e.getMessage());
                    }
                }
            }
        }, "ConnectionManager");
        
        managerThread.start();
    }

    private void handleIncomingConnection(Socket socket) {
        MessageCodec codec = new MessageCodec(format);
        PeerConnection connection = null;
        
        try {
            // Create temporary connection to read hello message
            connection = new PeerConnection(socket, codec, "unknown");
            
            // Wait for !hello command (first read message should be !hello)
            String firstMessage = connection.read();
            if (firstMessage == null) {
                connection.close();
                eventHandler.onProtocolError("Connection closed before hello");
                return;
            }

            ProtocolMessage helloMsg = ProtocolParser.parse(firstMessage, "peer");
            if (helloMsg.getCommand() != ProtocolCommand.HELLO) {
                connection.close();
                eventHandler.onProtocolError("Expected !hello, got: " + firstMessage);
                return;
            }

            String peerName = helloMsg.getContent();
            // Update the connection with the actual peer name
            connection.setPeerName(peerName);
            
            // Send !ack (this acknowledges that the connection was established successfully)
            connection.send(ProtocolParser.formatAck());
            connection.flush();

            // Connection established successfully
            connections.put(peerName, connection);
            eventHandler.onConnectionEstablished(connection);

            // Start message reader and sender threads for this connection
            startMessageReader(connection);
            startMessageSender(connection);

        } catch (IOException e) {
            if (connection != null) {
                connection.close();
            } else if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException ignored) {}
            }
            eventHandler.onProtocolError("Error handling incoming connection: " + e.getMessage());
        }
    }

    /**
     * Connect to a peer and establish protocol handshake.
     */
    public PeerConnection connectToPeer(String host, int port, String peerName) throws IOException {
        Socket socket = new Socket(host, port);
        MessageCodec codec = new MessageCodec(format);
        
        PeerConnection connection = new PeerConnection(socket, codec, peerName);
        
        // Send !hello
        connection.send(ProtocolParser.formatHello(myName));
        connection.flush();

        // Wait for !ack
        String ackMessage = connection.read();
        if (ackMessage == null) {
            connection.close();
            throw new IOException("Connection closed before ack");
        }

        ProtocolMessage ackMsg = ProtocolParser.parse(ackMessage, peerName);
        if (ackMsg.getCommand() != ProtocolCommand.ACK) {
            connection.close();
            throw new IOException("Expected !ack, got: " + ackMessage);
        }

        // Handshake complete - initiator reads !ack, receiver sends !ack

        connections.put(peerName, connection);
        eventHandler.onConnectionEstablished(connection);

        // Start message reader and sender threads for this connection
        startMessageReader(connection);
        startMessageSender(connection);

        return connection;
    }

    /**
     * Start a message reader thread/task for a connection.
     */
    private void startMessageReader(PeerConnection connection) {
        executorService.submit(() -> {
            String peerName = connection.getPeerName();
            System.out.println("[MessageReader] Started for " + peerName);

            while (connection.isConnected() && running.get()) {
                try {
                    String message = connection.read();
                    if (message == null) {
                        break; // Connection closed
                    }

                    ProtocolMessage protocolMsg = ProtocolParser.parse(message, peerName);
                    
                    if (protocolMsg.getCommand() == ProtocolCommand.BYE) {
                        System.out.println("[MessageReader] Received !bye from " + peerName);
                        closeConnection(peerName);
                        break;
                    } else if (protocolMsg.getCommand() == ProtocolCommand.BYEBYE) {
                        System.out.println("[MessageReader] Received !byebye from " + peerName);
                        closeAllConnections();
                        return;
                    } else {
                        eventHandler.onMessageReceived(connection, message);
                    }
                } catch (Exception e) {
                    System.err.println("[MessageReader] Error reading from " + peerName + ": " + e.getMessage());
                    break;
                }
            }

            closeConnection(peerName);
        });
    }

    /**
     * Start a message sender thread/task for a connection.
     */
    private void startMessageSender(PeerConnection connection) {
        executorService.submit(() -> {
            String peerName = connection.getPeerName();
            System.out.println("[MessageSender] Started for " + peerName);

            while (connection.isConnected() && running.get()) {
                try {
                    connection.flush();
                    Thread.sleep(10); // Small delay to avoid busy-waiting
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (IOException e) {
                    System.err.println("[MessageSender] Error writing to " + peerName + ": " + e.getMessage());
                    break;
                }
            }
        });
    }

    public void sendMessage(String peerName, String message) {
        PeerConnection connection = connections.get(peerName);
        if (connection != null && connection.isConnected()) {
            connection.send(message);
        } else {
            System.err.println("[ConnectionManager] Connection not found or closed: " + peerName);
        }
    }

    public void sendBye(String peerName) {
        PeerConnection connection = connections.get(peerName);
        if (connection != null) {
            connection.send(ProtocolParser.formatBye());
            try {
                connection.flush();
            } catch (IOException e) {
                System.err.println("[ConnectionManager] Error sending !bye: " + e.getMessage());
            }
            closeConnection(peerName);
        }
    }

    public void sendByebye() {
        for (String peerName : connections.keySet()) {
            PeerConnection connection = connections.get(peerName);
            if (connection != null) {
                connection.send(ProtocolParser.formatByebye());
                try {
                    connection.flush();
                } catch (IOException e) {
                    // Ignore errors when closing
                }
            }
        }
        closeAllConnections();
    }

    private void closeConnection(String peerName) {
        PeerConnection connection = connections.remove(peerName);
        if (connection != null) {
            connection.close();
            eventHandler.onConnectionClosed(connection);
        }
    }

    private void closeAllConnections() {
        for (String peerName : connections.keySet()) {
            closeConnection(peerName);
        }
    }

    public void stop() {
        if (!running.compareAndSet(true, false)) {
            return;
        }

        sendByebye();
        closeAllConnections();

        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("[ConnectionManager] Error closing server socket: " + e.getMessage());
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("[ConnectionManager] Stopped");
    }

    public Map<String, PeerConnection> getConnections() {
        return new ConcurrentHashMap<>(connections);
    }

    public boolean isRunning() {
        return running.get();
    }
}

