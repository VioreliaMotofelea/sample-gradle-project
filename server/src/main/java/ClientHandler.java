import java.io.*;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final ConcurrentHashMap<String, Socket> clientMap;
    private String clientName;

    public ClientHandler(Socket socket, ConcurrentHashMap<String, Socket> clientMap) {
        this.clientSocket = socket;
        this.clientMap = clientMap;
    }


    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            handleConnection(in, out);
            System.out.println("Client '" + clientName + "' joined.");

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(clientName + ": " + message);

                if("!bye".equalsIgnoreCase(message))
                {
                    out.println("!bye");
                    break;
                }
                else {
                    out.println("echo " + message);
                }
            }
        } catch (IOException e) {
            System.err.println("Error handling client '" + clientName + "': " + e.getMessage());
        } finally {
            closeConnection();
        }
    }

    private void handleConnection(BufferedReader in, PrintWriter out) throws IOException {
        String hello = in.readLine();
        String name = hello.split(" ")[1];
        if (name == null || name.trim().isEmpty() || clientMap.containsKey(name)) {
            out.println("Username invalid or already taken. Try again.");
            throw new IOException("Invalid user");
        }
        clientName = name;
        clientMap.put(clientName, clientSocket);
        out.println("!ack");
    }


    private void closeConnection() {
        try {
            System.out.println("Client disconnected: " + clientName);
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}
