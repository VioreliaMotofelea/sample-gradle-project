package com.viorelia.p2p.chat.lab4;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Client implementation using Socket.
 * Demonstrates proper usage of Socket with read/write operations.
 * Uses separate threads for reading and writing (application of thread pools).
 */
public class SocketClient {
    private final String host;
    private final int port;
    private final MessageFormat format;
    private Socket socket;
    private ExecutorService executorService;
    private volatile boolean running = false;

    public SocketClient(String host, int port, MessageFormat format) {
        this.host = host;
        this.port = port;
        this.format = format;
        this.executorService = Executors.newFixedThreadPool(2);
    }

    public void connect() throws IOException {
        socket = new Socket(host, port);
        running = true;
        System.out.println("[Client] Connected to " + host + ":" + port + " (Format: " + format + ")");
        System.out.println("[Client] Socket isConnected: " + socket.isConnected() + ", isClosed: " + socket.isClosed());

        // Create codec - thread-safe, can be shared
        final MessageCodec codec = new MessageCodec(format);

        // Create shared streams - socket streams are thread-safe for read/write
        final BufferedReader reader = new BufferedReader(
            new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        final BufferedWriter writer = new BufferedWriter(
            new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

        // Reader thread/task - reads messages from server
        executorService.submit(() -> {
            System.out.println("[Client] Reader thread STARTED");
            try {
                String line;
                while (running && socket.isConnected() && !socket.isClosed()) {
                    try {
                        line = reader.readLine();
                        if (line == null) {
                            System.out.println("[Client] Reader: readLine() returned null - connection closed");
                            break;
                        }
                        System.out.println("[Client] Raw line received: '" + line + "'");
                        try {
                            String decoded = codec.decode(line);
                            System.out.println("[Client] <- " + decoded);
                        } catch (IOException e) {
                            System.err.println("[Client] Error decoding message: " + e.getMessage());
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        if (running) {
                            System.err.println("[Client] Reader IOException: " + e.getMessage());
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                System.out.println("[Client] Reader thread ENDED");
            } catch (Exception e) {
                System.err.println("[Client] Reader thread exception: " + e.getMessage());
                e.printStackTrace();
            } finally {
                running = false;
            }
        });

        // Small delay to ensure reader thread starts
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Sender thread/task - sends messages to server
        executorService.submit(() -> {
            System.out.println("[Client] Sender thread STARTED - ready for input");
            Scanner scanner = new Scanner(System.in);
            
            try {
                String line;
                while (running && socket.isConnected() && !socket.isClosed()) {
                    try {
                        // nextLine() blocks until input is available
                        line = scanner.nextLine();
                        
                        if (!running) {
                            System.out.println("[Client] Sender: running=false, exiting");
                            break;
                        }
                        
                        System.out.println("[Client] Sender: Got input: '" + line + "'");
                        try {
                            String encoded = codec.encode(line);
                            System.out.println("[Client] Sender: Encoding '" + line + "' -> '" + encoded + "'");
                            
                            synchronized (writer) {
                                writer.write(encoded);
                                writer.newLine();
                                writer.flush();
                            }
                            System.out.println("[Client] -> " + line + " (sent successfully)");
                        } catch (IOException e) {
                            System.err.println("[Client] Error sending message: " + e.getMessage());
                            e.printStackTrace();
                            break;
                        }
                    } catch (java.util.NoSuchElementException e) {
                        // Scanner closed or stdin ended
                        System.out.println("[Client] Sender: Input stream ended");
                        break;
                    } catch (Exception e) {
                        if (running) {
                            System.err.println("[Client] Sender error: " + e.getMessage());
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                System.out.println("[Client] Sender thread ENDED");
            } catch (Exception e) {
                System.err.println("[Client] Sender thread exception: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    scanner.close();
                } catch (Exception e) {
                    // Ignore
                }
                running = false;
            }
        });
    }

    public void disconnect() {
        running = false;
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.err.println("[Client] Error closing socket: " + e.getMessage());
        }
        executorService.shutdown();
        System.out.println("[Client] Disconnected");
    }

    public boolean isRunning() {
        return running;
    }
}

