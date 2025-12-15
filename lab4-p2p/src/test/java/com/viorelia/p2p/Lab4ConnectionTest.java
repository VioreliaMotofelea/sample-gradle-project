package com.viorelia.p2p;

import org.junit.jupiter.api.Test;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

public class Lab4ConnectionTest {

    @Test
    public void testEchoServer() throws Exception {
        int port = 5001; // Use different port to avoid conflicts
        ExecutorService serverPool = Executors.newCachedThreadPool();
        
        // Start server
        ServerSocket serverSocket = new ServerSocket(port);
        serverPool.submit(() -> {
            try {
                Socket clientSocket = serverSocket.accept();
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
                     BufferedWriter out = new BufferedWriter(
                        new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8))) {
                    
                    String line;
                    while ((line = in.readLine()) != null) {
                        out.write("echo: " + line);
                        out.newLine();
                        out.flush();
                    }
                }
            } catch (IOException e) {
                // Expected when test completes
            }
        });
        
        // Give server time to start
        Thread.sleep(100);
        
        // Connect client and send message
        try (Socket clientSocket = new Socket("localhost", port);
             BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
             BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8))) {
            
            // Send test message
            String testMessage = "Hello Test";
            out.write(testMessage);
            out.newLine();
            out.flush();
            
            // Read echo
            String response = in.readLine();
            assertEquals("echo: " + testMessage, response);
        } finally {
            serverSocket.close();
            serverPool.shutdown();
        }
    }
}

