import client.Client;
import client.IClient;
import server.IServer;
import server.Server;

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class P2PServer {
    private static final ConcurrentHashMap<String, Socket> clientMap = new ConcurrentHashMap<>();

    public static void main(String[] args) throws InterruptedException {
        if(args.length < 2)
        {
            System.out.println("Please provide a port number and username");
            return;
        }

        int PORT = Integer.parseInt(args[0]);
        String username = args[1];

        IServer server = new Server(PORT, clientMap, username);
        Thread serverThread = new Thread(server);
        serverThread.start();


        IClient client = new Client(username, PORT, clientMap);
        boolean clientEnded = client.start();
        if (clientEnded) {
//            System.err.println("Client ended");
            server.terminate();
        }
//        System.err.println("Server ended");
        serverThread.join();
//        System.err.println("and joined.");
    }
}
