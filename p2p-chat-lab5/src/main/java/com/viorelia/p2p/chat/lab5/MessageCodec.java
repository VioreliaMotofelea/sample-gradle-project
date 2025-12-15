package com.viorelia.p2p.chat.lab5;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;

/**
 * Codec for encoding/decoding messages in different formats.
 * Supports RAW, JSON (bonus), and Protobuf (bonus) formats.
 */
public class MessageCodec {
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private final MessageFormat format;

    public MessageCodec(MessageFormat format) {
        this.format = format;
    }

    public String encode(String message) throws IOException {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }

        return switch (format) {
            case RAW -> message;
            case JSON -> encodeJson(message);
            case PROTOBUF -> encodeProtobuf(message);
        };
    }

    public String decode(String encoded) throws IOException {
        if (encoded == null) {
            throw new IllegalArgumentException("Encoded message cannot be null");
        }

        return switch (format) {
            case RAW -> encoded;
            case JSON -> decodeJson(encoded);
            case PROTOBUF -> decodeProtobuf(encoded);
        };
    }

    private String encodeJson(String message) throws IOException {
        Map<String, Object> json = Map.of(
            "message", message,
            "timestamp", System.currentTimeMillis()
        );
        return JSON_MAPPER.writeValueAsString(json);
    }

    private String decodeJson(String json) throws IOException {
        @SuppressWarnings("unchecked")
        Map<String, Object> map = JSON_MAPPER.readValue(json, Map.class);
        return String.valueOf(map.get("message"));
    }

    private String encodeProtobuf(String message) {
        byte[] bytes = message.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        int length = bytes.length;
        StringBuilder sb = new StringBuilder();
        sb.append(length).append(':');
        sb.append(java.util.Base64.getEncoder().encodeToString(bytes));
        return sb.toString();
    }

    private String decodeProtobuf(String encoded) throws IOException {
        try {
            int colonIndex = encoded.indexOf(':');
            if (colonIndex == -1) {
                throw new IOException("Invalid protobuf format");
            }
            int length = Integer.parseInt(encoded.substring(0, colonIndex));
            String base64 = encoded.substring(colonIndex + 1);
            byte[] bytes = java.util.Base64.getDecoder().decode(base64);
            if (bytes.length != length) {
                throw new IOException("Length mismatch in protobuf message");
            }
            return new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IOException("Failed to decode protobuf message: " + e.getMessage(), e);
        }
    }

    public MessageFormat getFormat() {
        return format;
    }
}

