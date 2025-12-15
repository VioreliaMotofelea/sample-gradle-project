package com.viorelia.p2p.chat.lab4;

import java.io.IOException;

/**
 * Lab 4 - Basic socket/messaging library usage.
 * 
 * Requirements:
 * - Proper usage of ServerSocket or messaging library (2.5p)
 * - Read/write message (6p)
 * - Extra for sockets (1.5p)
 * - Bonus: JSON, Protobuf, others (5p)
 * 
 * Usage:
 *   Server mode: java P2PChatLab4 server <port> [--json|--protobuf]
 *   Client mode: java P2PChatLab4 client <host> <port> [--json|--protobuf]
 */
public class P2PChatLab4 {
    public static void main(String[] args) {
        if (args.length < 1) {
            printUsage();
            return;
        }

        String mode = args[0].toLowerCase();
        MessageFormat format = MessageFormat.RAW;

        // Parse format option
        for (String arg : args) {
            if ("--json".equalsIgnoreCase(arg)) {
                format = MessageFormat.JSON;
            } else if ("--protobuf".equalsIgnoreCase(arg)) {
                format = MessageFormat.PROTOBUF;
            }
        }

        try {
            if ("server".equals(mode)) {
                if (args.length < 2) {
                    printUsage();
                    return;
                }
                int port = Integer.parseInt(args[1]);
                runServer(port, format);
            } else if ("client".equals(mode)) {
                if (args.length < 3) {
                    printUsage();
                    return;
                }
                String host = args[1];
                int port = Integer.parseInt(args[2]);
                runClient(host, port, format);
            } else {
                System.err.println("Unknown mode: " + mode);
                printUsage();
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void runServer(int port, MessageFormat format) throws IOException {
        SocketServer server = new SocketServer(port, format);
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n[Server] Shutting down...");
            server.stop();
        }));

        server.start();

        // Keep server running
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void runClient(String host, int port, MessageFormat format) throws IOException {
        SocketClient client = new SocketClient(host, port, format);
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("\n[Client] Disconnecting...");
            client.disconnect();
        }));

        client.connect();

        // Keep client running
        while (client.isRunning()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  Server: java P2PChatLab4 server <port> [--json|--protobuf]");
        System.out.println("  Client: java P2PChatLab4 client <host> <port> [--json|--protobuf]");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java P2PChatLab4 server 5000");
        System.out.println("  java P2PChatLab4 server 5000 --json");
        System.out.println("  java P2PChatLab4 server 5000 --protobuf");
        System.out.println("  java P2PChatLab4 client localhost 5000");
        System.out.println("  java P2PChatLab4 client localhost 5000 --json");
    }
}

