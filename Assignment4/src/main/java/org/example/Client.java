package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private ConnectionManager connectionManager;

    private String serverAddr;
    private int serverPort;

    public Client(String serverAddr, int serverPort) {
        connectionManager = ConnectionManager.getInstance();

        this.serverAddr = serverAddr;
        this.serverPort = serverPort;
    }

    public void start() {
        try {
            Socket clientSocket = new Socket(serverAddr, serverPort);
            handleServer(clientSocket);
    } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleServer(Socket serverSocket) {
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            connectionManager.addConnection(new Peer(serverSocket.getInetAddress(), serverSocket.getPort()));

            out = new PrintWriter(serverSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

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
                serverSocket.close();
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
