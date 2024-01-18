package lab5;

import lab5.api.Server;

public class ServerMain {
    public static void main(String[] args) {
        try(Server server = new Server(8080)) {
            server.run();
        }
    }
}
