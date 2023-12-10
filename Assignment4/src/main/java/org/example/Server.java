package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ConnectionManager connectionManager;
    private ExecutorService executorService;
    private int port;

    public Server(int port) {
        connectionManager = ConnectionManager.getInstance();
        executorService = Executors.newFixedThreadPool(3);
        this.port = port;
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error when starting server: " + e.getMessage());
        }
    }

    private void handleClient(Socket clientSocket) {
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            connectionManager.addConnection(new Peer(clientSocket.getInetAddress(), clientSocket.getPort()));

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            BufferedReader finalIn = in;
            Thread readThread = new Thread(() -> readMessages(finalIn));
            PrintWriter finalOut = out;
            Thread writeThread = new Thread(() -> writeMessages(finalOut));

            readThread.start();
            writeThread.start();

            readThread.join();
            writeThread.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void readMessages(BufferedReader in) {
        try {
            while (true) {
                String message = in.readLine();
                if (message == null) {
                    break;
                }
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeMessages(PrintWriter out) {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                String message = scanner.nextLine();
                out.println(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
