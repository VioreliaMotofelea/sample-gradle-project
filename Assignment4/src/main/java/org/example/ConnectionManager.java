package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionManager {

    private static ConnectionManager instance = null;

    private Map<Peer, List<Message>> connections;

    private ConnectionManager() {
        connections = new HashMap<>();
    }

    public void addConnection(Peer peer) {
        connections.put(peer, new ArrayList<>());
    }

    public List<String> getConnectedPeersNames() {
        List<String> connectedPeersNames = new ArrayList<>();
        // TODO: change this to username
        for (Peer peer : connections.keySet())
            connectedPeersNames.add(peer.getPort() + " " + peer.getAddress().getHostAddress());
        return connectedPeersNames;
    }


    public static ConnectionManager getInstance() {
        if (instance == null)
            instance = new ConnectionManager();
        return instance;
    }
}
