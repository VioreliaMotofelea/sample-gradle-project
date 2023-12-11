package org.example;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

public class Peer {

    private InetAddress address;
    private int port;
    private InputStream inputStream;
    private OutputStream outputStream;

    public Peer(InetAddress address, int port, InputStream inputStream, OutputStream outputStream) {
        this.address = address;
        this.port = port;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Peer) {
            Peer peer = (Peer) obj;
            return peer.getAddress().equals(address) && peer.getPort() == port;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return address.hashCode() + port;
    }
}
