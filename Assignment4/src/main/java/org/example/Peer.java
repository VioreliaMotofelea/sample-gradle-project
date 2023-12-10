package org.example;

import java.net.InetAddress;

public class Peer {

    private InetAddress address;
    private int port;

    public Peer(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Peer) {
            Peer peer = (Peer) obj;
            return address.equals(peer.address) && port == peer.port;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return address.hashCode() + port;
    }
}
