package com.viorelia.p2p;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Lab4SocketDemo {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static boolean useJson = false;

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Usage:\n  server <port> [--json]\n  client <host> <port> [--json]");
            return;
        }
        String mode = args[0];

        for (String arg : args) {
            if ("--json".equalsIgnoreCase(arg)) {
                useJson = true;
                break;
            }
        }

        if ("server".equalsIgnoreCase(mode)) {
            int port = Integer.parseInt(args[1]);
            runServer(port);
        } else if ("client".equalsIgnoreCase(mode)) {
            String host = args[1];
            int port = Integer.parseInt(args[2]);
            runClient(host, port);
        } else {
            System.out.println("Unknown mode: " + mode);
        }
    }

    private static void runServer(int port) throws Exception {
        ExecutorService pool = Executors.newCachedThreadPool();
        try (ServerSocket ss = new ServerSocket(port)) {
            System.out.println("[server] listening on " + port);
            while (true) {
                Socket s = ss.accept();
                pool.submit(() -> handleClient(s));
            }
        }
    }

    private static void handleClient(Socket s) {
        System.out.println("[server] client connected: " + s.getRemoteSocketAddress());
        try (BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8))) {

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println("[server] recv: " + line);
                String response = useJson ? encodeJsonMessage("echo: " + line) : "echo: " + line;
                out.write(response);
                out.newLine();
                out.flush();
            }
        } catch (IOException e) {
            System.out.println("[server] client closed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[server] error: " + e.getMessage());
        } finally {
            try { s.close(); } catch (IOException ignored) {}
        }
    }

    private static void runClient(String host, int port) throws Exception {
        try (Socket s = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8));
             BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {

            System.out.println("[client] connected to " + host + ":" + port + (useJson ? " (JSON mode)" : ""));
            Thread reader = new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        String decoded = useJson ? decodeJsonMessage(line) : line;
                        System.out.println("[client] <- " + decoded);
                    }
                } catch (IOException ignored) {
                } catch (Exception e) {
                    System.out.println("[client] decode error: " + e.getMessage());
                }
            });
            reader.setDaemon(true);
            reader.start();

            String line;
            while ((line = stdin.readLine()) != null) {
                String encoded = useJson ? encodeJsonMessage(line) : line;
                out.write(encoded);
                out.newLine();
                out.flush();
            }
        }
    }

    private static String encodeJsonMessage(String text) {
        try {
            return JSON_MAPPER.writeValueAsString(Map.of("message", text));
        } catch (Exception e) {
            throw new RuntimeException("JSON encode error", e);
        }
    }

    private static String decodeJsonMessage(String json) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = JSON_MAPPER.readValue(json, Map.class);
            return String.valueOf(map.get("message"));
        } catch (Exception e) {
            return json; // fallback to raw
        }
    }
}

/*

# T1:
.\gradlew :lab4-p2p:run --args="server 5000"

# T2:
.\gradlew :lab4-p2p:run --args="client localhost 5000"

 */