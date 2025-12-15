package com.viorelia.p2p.chat.lab5;

/**
 * Represents a protocol message.
 */
public class ProtocolMessage {
    private final ProtocolCommand command;
    private final String content;
    private final String senderName;

    public ProtocolMessage(ProtocolCommand command, String content, String senderName) {
        this.command = command;
        this.content = content;
        this.senderName = senderName;
    }

    public ProtocolCommand getCommand() {
        return command;
    }

    public String getContent() {
        return content;
    }

    public String getSenderName() {
        return senderName;
    }

    @Override
    public String toString() {
        return "ProtocolMessage{command=" + command + ", content='" + content + "', sender='" + senderName + "'}";
    }
}

