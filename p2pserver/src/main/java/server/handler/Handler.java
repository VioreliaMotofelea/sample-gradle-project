package server.handler;

import messageFormat.Format;
import messageFormat.JsonFormat;
import messageFormat.Message;
import utils.Utils;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ConcurrentHashMap;

public class Handler implements Runnable {
    private final Socket peerSocket;
    private final ConcurrentHashMap<String, Socket> peerMap;
    private String peerUsername;
    private final String serverName;

    private final Format formatter = new JsonFormat();

    public Handler(Socket socket, ConcurrentHashMap<String, Socket> peerMap, String serverName) {
        this.peerSocket = socket;
        this.peerMap = peerMap;
        this.serverName = serverName;
    }

    public Handler(Socket socket, ConcurrentHashMap<String, Socket> peerMap, String serverName, String peerUsername) {
        this.peerSocket = socket;
        this.peerMap = peerMap;
        this.serverName = serverName;
        this.peerUsername = peerUsername;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(peerSocket.getInputStream()));
             PrintWriter out = new PrintWriter(peerSocket.getOutputStream(), true)) {
            if (peerUsername == null)
                handleIncomingConnection(in, out);
            Utils.safePrintln("Client '" + peerUsername + "' joined.");

            String message;
            while ((message = in.readLine()) != null) {
                Message messageFormatted = formatter.input(message);

                switch (messageFormatted.command) {
                    case "!message":
                        Utils.safePrintln(peerUsername + ": " + messageFormatted.message);
                        break;
                    case "!bye":
                    case "!byebye":
                        return;
                    default:
                        out.println("Invalid command: " + messageFormatted.command);
                }
            }
        } catch (SocketException ignored){
        } catch (IOException e) {
            System.err.println("Error handling client '" + peerUsername + "': " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    private void handleIncomingConnection(BufferedReader in, PrintWriter out) throws IOException {
        String response = in.readLine();
        Message responseMessage = formatter.input(response);
        if (responseMessage == null || !"!hello".equalsIgnoreCase(responseMessage.command)) {
            out.println("Invalid response. Try again.");
            throw new RuntimeException("Invalid response");
        }
        peerUsername = responseMessage.username;
        peerMap.put(peerUsername, peerSocket);
        out.println(formatter.output(new Message("!ack", serverName)));
    }


    private void closeConnection() {
        try {
            peerMap.remove(peerUsername);
            peerSocket.close();
            Utils.safePrintln("Client '" + peerUsername + "' left.");
        } catch (IOException e) {
            System.err.println("Error closing connection with client '" + peerUsername + "': " + e.getMessage());
        }
    }
}
