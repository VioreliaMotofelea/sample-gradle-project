package lab5.api;

import lab5.model.ENetworkCommand;
import lab5.utils.JsonSerializer;
import lab5.utils.NetworkUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable, AutoCloseable {
    private static final Logger log = LogManager.getLogger(Client.class);

    private final Socket socket;
    private final ObjectInputStream inStream;
    private final ObjectOutputStream outStream;
    private final JsonSerializer jsonSerializer;


    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.outStream = NetworkUtils.getOutputStream(socket);
        this.inStream = NetworkUtils.getInputStream(socket);

        this.jsonSerializer = JsonSerializer.LazyHolder.INSTANCE;
    }

    @Override
    public void run() {
        try {
            JsonSerializer.JsonMessage message = (JsonSerializer.JsonMessage) inStream.readObject();
            ENetworkCommand command = (ENetworkCommand) jsonSerializer.deserialize(message, ENetworkCommand.class);
            if(command == ENetworkCommand.INIT) {
                log.info("Client got init message!");
            }
            else {
                log.error("Client initialized, but received message is not ack_init");
            }
        } catch (IOException e) {
            log.error("While reading from socket: ", e);
        } catch (ClassNotFoundException e) {
            log.error("Message not in standard format, While reading from socket: ", e);
            throw new RuntimeException(e);
        }

        try {
            JsonSerializer.JsonMessage message = jsonSerializer.serialize(ENetworkCommand.ACK_INIT);
            outStream.writeObject(message);
            outStream.flush();
            log.info("Client sent ack_init message!");
        } catch (IOException e) {
            log.error("While writing to socket: ", e);
        }
        log.info("Client finished with success!");
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            log.error("While closing socket: ", e);
        }
    }
}
