package server;

import server.handler.Handler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ConcurrentHashMap;

public class Server implements IServer {
    final int port;
    final ConcurrentHashMap<String, Socket> clientMap;
    final String username;

    private volatile boolean running = true;
    private ServerSocket serverSocket;

    public Server(int port, ConcurrentHashMap<String, Socket> clientMap, String username) {
        this.port = port;
        this.clientMap = clientMap;
        this.username = username;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is listening on port " + port);

            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    Handler clientHandler = new Handler(clientSocket, clientMap, username);
                    new Thread(clientHandler).start();
                } catch (SocketException e) {
                    if (running) {
                        System.err.println("ServerSocket was unexpectedly closed: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("server.Server error: " + e.getMessage());
        }
    }

    public void terminate() {
        running = false;
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing server socket: " + e.getMessage());
            }
        }
    }
}
