package org.example;

import java.util.ArrayList;
import java.util.List;

public class MessageQ {

    private List<Message> messages;

    public MessageQ(List<Message> messages) {
        this.messages = messages;
    }

    public MessageQ() {
        this.messages = new ArrayList<>();
    }

    public synchronized void addMessage(Message message) {
        messages.add(message);
    }

    public synchronized List<Message> getMessages() {
        if (this.messages == null)
            this.messages = new ArrayList<>();
        return messages;
    }
}
