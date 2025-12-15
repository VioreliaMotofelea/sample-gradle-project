package com.viorelia.p2p;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

public class PeerNode {

    public static void main(String[] args) throws Exception {
        Config cfg = Config.parse(args);

        if (cfg.deadlockDemo()) {
            System.out.println("Running DEADLOCK demo...");
            DeadlockDemo.run();
            return;
        }

        System.out.println("Peer starting: name=" + cfg.name + " port=" + cfg.port + " json=" + cfg.json);

        ConnectionManager mgr = new ConnectionManager(cfg);
        mgr.startServer();

        System.out.println("""
Commands:
  !connect <host> <port>     connect to peer and send hello
  !bye <peerName>            close one connection
  !byebye                    close all and exit
  <peerName>: <message>      send message to a peer
""");

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = stdin.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (line.startsWith("!connect ")) {
                try {
                    String[] parts = line.split("\\s+");
                    if (parts.length < 3) {
                        System.out.println("[error] usage: !connect <host> <port>");
                        System.out.flush();
                        continue;
                    }
                    String host = parts[1];
                    int port = Integer.parseInt(parts[2]);
                    System.out.println("[cmd] connecting to " + host + ":" + port);
                    System.out.flush();
                    mgr.connect(host, port);
                } catch (NumberFormatException e) {
                    System.out.println("[error] invalid port number: " + e.getMessage());
                    System.out.flush();
                } catch (Exception e) {
                    System.out.println("[error] connection failed: " + e.getMessage());
                    e.printStackTrace();
                    System.out.flush();
                }
                continue;
            }

            if (line.startsWith("!bye ")) {
                mgr.closePeer(line.split("\\s+")[1]);
                continue;
            }

            if (line.equalsIgnoreCase("!byebye")) {
                mgr.shutdown();
                break;
            }

            int idx = line.indexOf(':');
            if (idx > 0) {
                String peer = line.substring(0, idx).trim();
                String msg  = line.substring(idx + 1).trim();
                mgr.sendChat(peer, msg);
            }
        }

        Thread.currentThread().join();
    }

    public record Config(String name, int port, boolean json, boolean deadlockDemo) {
        static Config parse(String[] args) {
            // --name Alice --port 5000 --json
            String name = "anon";
            int port = 5000; // ramane asta 5000 idk de ce <---
            boolean json = false;
            boolean deadlockDemo = false;

            for (int i = 0; i < args.length; i++) {
                String a = args[i].toLowerCase(Locale.ROOT);
                if (a.equals("--name") && i + 1 < args.length) name = args[++i];
                else if (a.equals("--port") && i + 1 < args.length) port = Integer.parseInt(args[++i]);
                else if (a.equals("--json")) json = true;
                else if (a.equals("--deadlock")) deadlockDemo = true;
            }
            return new Config(name, port, json, deadlockDemo);
        }
    }
}
