package com.viorelia.p2p.chat.lab4;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server implementation using ServerSocket.
 * Demonstrates proper usage of ServerSocket with read/write operations.
 */
public class SocketServer {
    private final int port;
    private final MessageFormat format;
    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private volatile boolean running = false;

    public SocketServer(int port, MessageFormat format) {
        this.port = port;
        this.format = format;
        this.executorService = Executors.newCachedThreadPool();
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        running = true;
        System.out.println("[Server] Listening on port " + port + " (Format: " + format + ")");

        executorService.submit(() -> {
            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("[Server] Client connected: " + clientSocket.getRemoteSocketAddress());
                    executorService.submit(() -> handleClient(clientSocket));
                } catch (IOException e) {
                    if (running) {
                        System.err.println("[Server] Error accepting connection: " + e.getMessage());
                    }
                }
            }
        });
    }

    private void handleClient(Socket clientSocket) {
        MessageCodec codec = new MessageCodec(format);
        System.out.println("[Server] Starting to handle client: " + clientSocket.getRemoteSocketAddress());
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8))) {

            System.out.println("[Server] Ready to read from client: " + clientSocket.getRemoteSocketAddress());
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[Server] Raw line received: '" + line + "'");
                try {
                    String decoded = codec.decode(line);
                    System.out.println("[Server] Decoded message: '" + decoded + "'");
                    
                    // Echo the message back
                    String response = "Echo: " + decoded;
                    String encoded = codec.encode(response);
                    System.out.println("[Server] Encoding response: '" + response + "' -> '" + encoded + "'");
                    
                    writer.write(encoded);
                    writer.newLine();
                    writer.flush();
                    
                    System.out.println("[Server] Sent successfully: " + response);
                } catch (IOException e) {
                    System.err.println("[Server] Error processing message: " + e.getMessage());
                    e.printStackTrace();
                    break;
                } catch (Exception e) {
                    System.err.println("[Server] Unexpected error: " + e.getMessage());
                    e.printStackTrace();
                    break;
                }
            }
            System.out.println("[Server] Client stream ended (readLine returned null)");
        } catch (IOException e) {
            System.out.println("[Server] Client disconnected: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (!clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.err.println("[Server] Error closing socket: " + e.getMessage());
            }
        }
    }

    public void stop() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("[Server] Error closing server socket: " + e.getMessage());
        }
        executorService.shutdown();
        System.out.println("[Server] Stopped");
    }
}

