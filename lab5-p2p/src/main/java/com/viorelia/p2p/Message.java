package com.viorelia.p2p;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public record Message(Type type, String from, String text) {
    enum Type { HELLO, ACK, CHAT, BYE }

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static Message hello(String from, boolean json) {
        return new Message(Type.HELLO, from, null);
    }

    public static Message ack(String from, boolean json) {
        return new Message(Type.ACK, from, null);
    }

    public static Message chat(String from, String text, boolean json) {
        return new Message(Type.CHAT, from, text);
    }

    public static Message bye(String from, boolean json) {
        return new Message(Type.BYE, from, null);
    }

    public String encode(boolean json) throws Exception {
        if (json) {
            Map<String, Object> m = new java.util.HashMap<>();
            m.put("type", type.name());
            m.put("from", from);

            if (text != null) {
                m.put("text", text);
            }

            return MAPPER.writeValueAsString(m);
        }

        return switch (type) {
            case HELLO -> "!hello " + from;
            case ACK -> "!ack";
            case BYE -> "!bye";
            case CHAT -> text;
        };
    }

    public static Message decode(String line, boolean json) {
        if (line == null || line.isEmpty()) {
            return new Message(Type.CHAT, null, "");
        }
        
        try {
            if (json) {
                @SuppressWarnings("unchecked")
                Map<String, Object> m = MAPPER.readValue(line, Map.class);
                Type t = Type.valueOf(String.valueOf(m.get("type")));
                String from = (String) m.get("from");
                String text = (String) m.get("text");
                return new Message(t, from, text);
            }

            line = line.trim();
            if (line.startsWith("!hello ")) return new Message(Type.HELLO, line.substring(7).trim(), null);
            if (line.equals("!ack")) return new Message(Type.ACK, null, null);
            if (line.equals("!bye")) return new Message(Type.BYE, null, null);
            return new Message(Type.CHAT, null, line);

        } catch (Exception e) {
            return new Message(Type.CHAT, null, line);
        }
    }
}


// message -> encode -> send -> receive -> decode -> same message
