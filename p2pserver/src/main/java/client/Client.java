package client;

import messageFormat.Format;
import messageFormat.JsonFormat;
import messageFormat.Message;
import server.handler.Handler;
import utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Client implements IClient {
    private final String username;
    private final int port;

    private final ConcurrentHashMap<String, Socket> peerMap;
    private final Format formatter = new JsonFormat();

    private volatile boolean running = true;

    public Client(String username, int port, ConcurrentHashMap<String, Socket> peerMap) {
        this.username = username;
        this.port = port;
        this.peerMap = peerMap;
    }

    @Override
    public boolean start() {
        try (BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("!connect <port>; !message <username> <message>; !bye <username>; !byebye");
            while (running) {
//                System.err.println("DEBUG: " + peerMap.toString());
                String command = consoleInput.readLine();
                String[] commandSplit = command.split(" ", 3);
                switch (commandSplit[0]) {
                    case "!connect":
                        connect(Integer.parseInt(commandSplit[1]));
                        break;
                    case "!message":
                        send(new Message(commandSplit[0], commandSplit[1], commandSplit[2]));
                        break;
                    case "!bye":
                        disconnectFrom(new Message(commandSplit[0], commandSplit[1]));
                        break;
                    case "!byebye":
                        return true;
                    default:
                        Utils.safePrintln("Invalid command: " + commandSplit[0]);
                }

            }
        } catch (NumberFormatException e) {
            System.err.println("Port must be number");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            disconnectFromAll();
        }
        return false;
    }

    @Override
    public void connect(int port) {
        try {
            Socket peerSocket = new Socket("localhost", port);
            BufferedReader in = new BufferedReader(new InputStreamReader(peerSocket.getInputStream()));
            PrintWriter out = new PrintWriter(peerSocket.getOutputStream(), true);

            Message helloMessage = new Message("!hello", username);
            out.println(formatter.output(helloMessage));

            String response = in.readLine();
            Message responseMessage = formatter.input(response);
            if (responseMessage == null || !"!ack".equalsIgnoreCase(responseMessage.command)) {
                System.err.println("Server didnot acknowledge connection. Try again.");
            }

            Utils.safePrintln("Connected to " + responseMessage.username);
            peerMap.put(responseMessage.username, peerSocket); // Keep the socket open
            Handler clientHandler = new Handler(peerSocket, peerMap, username, responseMessage.username);
            new Thread(clientHandler).start();
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }

    @Override
    public void send(Message sendMessage) {
        Socket peerSocket = peerMap.get(sendMessage.username);
        if (peerSocket == null) {
            System.err.println("No connection to user: " + sendMessage.username);
            return;
        }

        try {
            PrintWriter out = new PrintWriter(peerSocket.getOutputStream(), true);
            out.println(formatter.output(sendMessage));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disconnectFrom(Message byeMessage) {
        Socket peerSocket = peerMap.get(byeMessage.username);
        if (peerSocket == null) {
            System.err.println("No connection to user: " + byeMessage.username);
            return;
        }
        try {
            PrintWriter out = new PrintWriter(peerSocket.getOutputStream(), true);
            out.println(formatter.output(byeMessage));
            peerSocket.close();
            peerMap.remove(byeMessage.username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void disconnectFromAll() {
        for (Map.Entry<String, Socket> entry : peerMap.entrySet()) {
            String username = entry.getKey();
            Socket peerSocket = entry.getValue();

            try {
                PrintWriter out = new PrintWriter(peerSocket.getOutputStream(), true);
                out.println(formatter.output(new Message("!byebye")));
                peerSocket.close();
            } catch (IOException e) {
                System.err.println("Error disconnecting from " + username + ": " + e.getMessage());
            }
        }
        peerMap.clear();
        running = false;
    }
}
