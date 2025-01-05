package server;

public interface IServer extends Runnable {
    void run();

    void terminate();
}
