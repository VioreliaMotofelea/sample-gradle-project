package com.viorelia.p2p.chat.lab5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Lab 5 - Full P2P chat protocol implementation.
 * 
 * Protocol Requirements:
 * - !hello <name> - initialize connection (first read message should be !ack)
 * - !ack - acknowledge connection establishment
 * - !bye - close connection
 * - !byebye - close all connections and exit
 * 
 * Implementation Requirements:
 * - Protocol implementation (5p)
 * - Exceptions (2p)
 * - Concurrency (1.5p)
 * - Extra for socket (1.5p)
 * - Bonus: deadlock prevention (10p)
 * - Bonus: JSON, Protobuf support
 * 
 * Usage:
 *   java P2PChatLab5 <name> <listen-port> [--json|--protobuf]
 */
public class P2PChatLab5 implements ConnectionManager.ConnectionEventHandler {
    private final String myName;
    private final int listenPort;
    private final MessageFormat format;
    private ConnectionManager connectionManager;
    private final BufferedReader stdin = new BufferedReader(
        new InputStreamReader(System.in, StandardCharsets.UTF_8));
    private volatile boolean running = true;

    public P2PChatLab5(String myName, int listenPort, MessageFormat format) {
        this.myName = myName;
        this.listenPort = listenPort;
        this.format = format;
    }

    public void start() throws IOException {
        connectionManager = new ConnectionManager(listenPort, format, myName, this);
        connectionManager.start();

        System.out.println("=== P2P Chat Started ===");
        System.out.println("Name: " + myName);
        System.out.println("Listening on port: " + listenPort);
        System.out.println("Format: " + format);
        System.out.println();
        System.out.println("Commands:");
        System.out.println("  connect <host> <port> <peer-name> - Connect to a peer");
        System.out.println("  send <peer-name> <message> - Send message to peer");
        System.out.println("  bye <peer-name> - Close connection to peer");
        System.out.println("  byebye - Close all connections and exit");
        System.out.println("  list - List all connections");
        System.out.println("  quit - Exit");
        System.out.println();

        // Start input handler thread
        CompletableFuture.runAsync(() -> {
            try {
                handleUserInput();
            } catch (IOException e) {
                System.err.println("Error reading input: " + e.getMessage());
            }
        });

        // Keep main thread alive
        try {
            while (running && connectionManager.isRunning()) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        shutdown();
    }

    private void handleUserInput() throws IOException {
        while (running) {
            System.out.print("> ");
            String line = stdin.readLine();
            if (line == null || !running) {
                break;
            }

            String[] parts = line.trim().split("\\s+", 3);
            if (parts.length == 0 || parts[0].isEmpty()) {
                continue;
            }

            String command = parts[0].toLowerCase();

            try {
                switch (command) {
                    case "connect":
                        if (parts.length < 4) {
                            System.out.println("Usage: connect <host> <port> <peer-name>");
                            break;
                        }
                        handleConnect(parts[1], Integer.parseInt(parts[2]), parts[3]);
                        break;

                    case "send":
                        if (parts.length < 3) {
                            System.out.println("Usage: send <peer-name> <message>");
                            break;
                        }
                        handleSend(parts[1], parts[2]);
                        break;

                    case "bye":
                        if (parts.length < 2) {
                            System.out.println("Usage: bye <peer-name>");
                            break;
                        }
                        handleBye(parts[1]);
                        break;

                    case "byebye":
                        handleByebye();
                        running = false;
                        break;

                    case "list":
                        handleList();
                        break;

                    case "quit":
                    case "exit":
                        handleByebye();
                        running = false;
                        break;

                    default:
                        System.out.println("Unknown command: " + command);
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void handleConnect(String host, int port, String peerName) {
        try {
            System.out.println("[Connecting] to " + host + ":" + port + " as " + peerName);
            connectionManager.connectToPeer(host, port, peerName);
            System.out.println("[Connected] to " + peerName);
        } catch (IOException e) {
            System.out.println("[Error] Failed to connect: " + e.getMessage());
        }
    }

    private void handleSend(String peerName, String message) {
        connectionManager.sendMessage(peerName, message);
        System.out.println("[Sent] to " + peerName + ": " + message);
    }

    private void handleBye(String peerName) {
        connectionManager.sendBye(peerName);
        System.out.println("[Closed] connection to " + peerName);
    }

    private void handleByebye() {
        connectionManager.sendByebye();
        System.out.println("[Closed] all connections");
    }

    private void handleList() {
        Map<String, PeerConnection> connections = connectionManager.getConnections();
        if (connections.isEmpty()) {
            System.out.println("[List] No active connections");
        } else {
            System.out.println("[List] Active connections:");
            for (String peerName : connections.keySet()) {
                PeerConnection conn = connections.get(peerName);
                System.out.println("  - " + peerName + " (" + 
                    (conn.isConnected() ? "connected" : "disconnected") + ")");
            }
        }
    }

    private void shutdown() {
        running = false;
        if (connectionManager != null) {
            connectionManager.stop();
        }
        System.out.println("=== P2P Chat Stopped ===");
    }

    // ConnectionEventHandler implementation
    @Override
    public void onConnectionEstablished(PeerConnection connection) {
        System.out.println("[Event] Connection established with " + connection.getPeerName());
    }

    @Override
    public void onConnectionClosed(PeerConnection connection) {
        System.out.println("[Event] Connection closed with " + connection.getPeerName());
    }

    @Override
    public void onMessageReceived(PeerConnection connection, String message) {
        System.out.println("[" + connection.getPeerName() + "] " + message);
    }

    @Override
    public void onProtocolError(String error) {
        System.err.println("[Protocol Error] " + error);
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            printUsage();
            return;
        }

        String name = args[0];
        int listenPort;
        
        try {
            listenPort = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Invalid port number: " + args[1]);
            printUsage();
            return;
        }

        MessageFormat format = MessageFormat.RAW;
        for (String arg : args) {
            if ("--json".equalsIgnoreCase(arg)) {
                format = MessageFormat.JSON;
            } else if ("--protobuf".equalsIgnoreCase(arg)) {
                format = MessageFormat.PROTOBUF;
            }
        }

        try {
            P2PChatLab5 chat = new P2PChatLab5(name, listenPort, format);
            
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\n[Shutdown] Initiating shutdown...");
                chat.shutdown();
            }));

            chat.start();
        } catch (Exception e) {
            System.err.println("Fatal error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java P2PChatLab5 <name> <listen-port> [--json|--protobuf]");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java P2PChatLab5 Alice 5000");
        System.out.println("  java P2PChatLab5 Bob 5001 --json");
        System.out.println("  java P2PChatLab5 Charlie 5002 --protobuf");
    }
}

