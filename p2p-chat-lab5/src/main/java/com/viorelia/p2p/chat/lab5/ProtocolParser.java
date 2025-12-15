package com.viorelia.p2p.chat.lab5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for protocol commands.
 */
public class ProtocolParser {
    private static final Pattern HELLO_PATTERN = Pattern.compile("^!hello\\s+(.+)$", Pattern.CASE_INSENSITIVE);
    private static final Pattern ACK_PATTERN = Pattern.compile("^!ack$", Pattern.CASE_INSENSITIVE);
    private static final Pattern BYE_PATTERN = Pattern.compile("^!bye$", Pattern.CASE_INSENSITIVE);
    private static final Pattern BYEBYE_PATTERN = Pattern.compile("^!byebye$", Pattern.CASE_INSENSITIVE);

    public static ProtocolMessage parse(String rawMessage, String senderName) {
        if (rawMessage == null || rawMessage.trim().isEmpty()) {
            return new ProtocolMessage(ProtocolCommand.MESSAGE, rawMessage, senderName);
        }

        String trimmed = rawMessage.trim();

        Matcher helloMatcher = HELLO_PATTERN.matcher(trimmed);
        if (helloMatcher.matches()) {
            String name = helloMatcher.group(1).trim();
            return new ProtocolMessage(ProtocolCommand.HELLO, name, senderName);
        }

        if (ACK_PATTERN.matcher(trimmed).matches()) {
            return new ProtocolMessage(ProtocolCommand.ACK, "", senderName);
        }

        if (BYE_PATTERN.matcher(trimmed).matches()) {
            return new ProtocolMessage(ProtocolCommand.BYE, "", senderName);
        }

        if (BYEBYE_PATTERN.matcher(trimmed).matches()) {
            return new ProtocolMessage(ProtocolCommand.BYEBYE, "", senderName);
        }

        return new ProtocolMessage(ProtocolCommand.MESSAGE, rawMessage, senderName);
    }

    public static String formatHello(String name) {
        return "!hello " + name;
    }

    public static String formatAck() {
        return "!ack";
    }

    public static String formatBye() {
        return "!bye";
    }

    public static String formatByebye() {
        return "!byebye";
    }
}

