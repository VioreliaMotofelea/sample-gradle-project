package lab5.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

public class NetworkUtils {
    private static final Logger log = LogManager.getLogger(NetworkUtils.class);

    public static ObjectInputStream getInputStream(Socket socket) {
        try {
            return new ObjectInputStream(
                    new BufferedInputStream(
                            socket.getInputStream()
                    )
            );
        } catch (Exception e) {
            log.fatal("While getting input stream: ", e);
            throw new RuntimeException(e);
        }
    }

    public static ObjectOutputStream getOutputStream(Socket socket) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    new BufferedOutputStream(
                            socket.getOutputStream()
                    )
            );
            objectOutputStream.flush();
            return objectOutputStream;
        } catch (Exception e) {
            log.fatal("While getting output stream: ", e);
            throw new RuntimeException(e);
        }
    }
}
