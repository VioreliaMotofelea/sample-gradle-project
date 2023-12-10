package org.example;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

public class PeerAddressInfo {

    private InetAddress address;
    private int port;


    public PeerAddressInfo(InetAddress address, int port) {
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
            return peer.getAddress().equals(address) && peer.getPort() == port;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return address.hashCode() + port;
    }
}
