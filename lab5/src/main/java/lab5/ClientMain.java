package lab5;

import lab5.api.Client;

public class ClientMain {
    public static void main(String[] args) {
        try(Client client = new Client("localhost", 8080)) {
            client.run();
        }
    }
}
