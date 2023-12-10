package org.example;

import java.util.Scanner;

public class UI {

    void UI() {}

    void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the chatroom!");
        System.out.println("Enter the port number you want to connect to: ");
        int port = scanner.nextInt();

//        System.out.println("Enter your username: ");
//        String username = scanner.next();

        try {
            new Server(port).start();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        while (true) {
            System.out.println("0. Exit");
            System.out.println("1. Connect to a peer");
            System.out.println("2. See peers");
            System.out.println("3. Go to peer chatroom");
            System.out.println("4. Disconnect from a peer");

            int option = scanner.nextInt();

            switch (option) {
                case 0:
                    System.out.println("Goodbye!");
                    return;
                case 1:
                    System.out.println("Enter the IP address of the peer you want to connect to: ");
                    String ip = scanner.next();
                    System.out.println("Enter the port number of the peer you want to connect to: ");
                    int peerPort = scanner.nextInt();

                    try {
                        new Client(ip, peerPort).start();
                        System.out.println("Connected to peer!");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;
                case 2:
                    System.out.println("Peers: ");
                    for (String peer : ConnectionManager.getInstance().getConnectedPeersNames()) {
                        System.out.println(peer);
                    }
                    break;
                case 3:
                    System.out.println("Enter the name of the peer you want to chat with: ");
                    String peerUsername = scanner.next();

                    break;
                case 4:
                    System.out.println("Enter the name of the peer you want to disconnect from: ");
                    String peerName = scanner.next();

                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
