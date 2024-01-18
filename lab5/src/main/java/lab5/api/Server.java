package lab5.api;

import lab5.model.ENetworkCommand;
import lab5.utils.JsonSerializer;
import lab5.utils.NetworkUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable, AutoCloseable {
    private static final Logger log = LogManager.getLogger(Server.class);

    private final Socket socket;
    private final ObjectInputStream inStream;
    private final ObjectOutputStream outStream;
    private final JsonSerializer jsonSerializer;


    public Server(int port) {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            socket = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.outStream = NetworkUtils.getOutputStream(socket);
        this.inStream = NetworkUtils.getInputStream(socket);

        jsonSerializer = JsonSerializer.LazyHolder.INSTANCE;
    }

    @Override
    public void run() {
        try {
            JsonSerializer.JsonMessage message = jsonSerializer.serialize(ENetworkCommand.INIT);
            outStream.writeObject(message);
            outStream.flush();
            log.info("Server sent init message!");
        } catch (IOException e) {
            log.error("While writing to socket: ", e);
        }

        try {
            JsonSerializer.JsonMessage message = (JsonSerializer.JsonMessage) inStream.readObject();
            ENetworkCommand command = (ENetworkCommand) jsonSerializer.deserialize(message, ENetworkCommand.class);
            if(command == ENetworkCommand.ACK_INIT) {
                log.info("Server got ack_init message!");
            }
            else {
                log.error("Server initialized, but received message is not ack_init");
            }
        } catch (IOException e) {
            log.error("While reading from socket: ", e);
        } catch (ClassNotFoundException e) {
            log.error("Message not in standard format, While reading from socket: ", e);
            throw new RuntimeException(e);
        }
        log.info("Server finished with success!");
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
