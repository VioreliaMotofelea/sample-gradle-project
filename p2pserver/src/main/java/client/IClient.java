package client;

import messageFormat.Message;

public interface IClient {
    boolean start();

    void connect(int port);

    void send(Message sendMessage);

    void disconnectFrom(Message byeMessage);

    void disconnectFromAll();
}
