package com.viorelia.p2p.chat.lab5;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Represents a peer connection with thread-safe read/write operations.
 * Handles proper exception handling and connection closing from multiple threads.
 * Implements deadlock prevention using read-write locks.
 */
public class PeerConnection {
    private final Socket socket;
    private final MessageCodec codec;
    private volatile String peerName;
    private final AtomicBoolean connected = new AtomicBoolean(true);
    private final AtomicBoolean closed = new AtomicBoolean(false);
    
    // Deadlock prevention: Use separate locks for reading and writing
    private final ReentrantReadWriteLock readLock = new ReentrantReadWriteLock();
    private final ReentrantReadWriteLock writeLock = new ReentrantReadWriteLock();
    
    private BufferedReader reader;
    private BufferedWriter writer;
    private final BlockingQueue<String> outgoingMessages = new LinkedBlockingQueue<>();

    public PeerConnection(Socket socket, MessageCodec codec, String peerName) throws IOException {
        this.socket = socket;
        this.codec = codec;
        this.peerName = peerName;
        this.reader = new BufferedReader(
            new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.writer = new BufferedWriter(
            new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
    }

    public String getPeerName() {
        return peerName;
    }

    public void setPeerName(String peerName) {
        this.peerName = peerName;
    }

    public Socket getSocket() {
        return socket;
    }

    public boolean isConnected() {
        return connected.get() && !socket.isClosed();
    }

    /**
     * Thread-safe read operation.
     * Returns null if connection is closed or error occurs.
     */
    public String read() {
        if (!isConnected() || closed.get()) {
            return null;
        }

        readLock.writeLock().lock();
        try {
            if (!isConnected() || closed.get()) {
                return null;
            }
            
            if (socket.isClosed() || !socket.isConnected()) {
                close();
                return null;
            }

            // Check if data is available to avoid blocking indefinitely
            if (socket.getInputStream().available() == 0 && socket.isClosed()) {
                return null;
            }

            String line = reader.readLine();
            if (line == null) {
                close();
                return null;
            }

            return codec.decode(line);
        } catch (IOException e) {
            // Handle exceptions when sender closes connection but reader still reads
            if (isConnected()) {
                System.err.println("[Connection] Read error for " + peerName + ": " + e.getMessage());
            }
            close();
            return null;
        } finally {
            readLock.writeLock().unlock();
        }
    }

    /**
     * Thread-safe write operation.
     * Queue message for sending to avoid blocking.
     */
    public void send(String message) {
        if (!isConnected() || closed.get()) {
            return;
        }

        try {
            outgoingMessages.put(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Flush queued messages to the socket.
     * This should be called from a dedicated sender thread.
     */
    public void flush() throws IOException {
        if (!isConnected() || closed.get()) {
            return;
        }

        writeLock.writeLock().lock();
        try {
            if (!isConnected() || closed.get()) {
                return;
            }

            // Process all queued messages
            while (!outgoingMessages.isEmpty() && isConnected()) {
                String message = outgoingMessages.poll();
                if (message != null) {
                    String encoded = codec.encode(message);
                    writer.write(encoded);
                    writer.newLine();
                    writer.flush();
                }
            }
        } catch (IOException e) {
            // Handle exceptions when connection is closed during write
            if (isConnected()) {
                System.err.println("[Connection] Write error for " + peerName + ": " + e.getMessage());
            }
            close();
            throw e;
        } finally {
            writeLock.writeLock().unlock();
        }
    }

    /**
     * Thread-safe close operation.
     * Can be safely called from multiple threads.
     */
    public void close() {
        if (!closed.compareAndSet(false, true)) {
            return; // Already closed
        }

        connected.set(false);

        // Close in a safe order to prevent deadlock
        readLock.writeLock().lock();
        writeLock.writeLock().lock();
        try {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                // Ignore errors when closing
            }

            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                // Ignore errors when closing
            }

            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                // Ignore errors when closing
            }
        } finally {
            writeLock.writeLock().unlock();
            readLock.writeLock().unlock();
        }
    }

    public boolean hasPendingMessages() {
        return !outgoingMessages.isEmpty();
    }
}

