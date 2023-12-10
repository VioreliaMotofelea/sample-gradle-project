package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MessageQ {

    private List<Message> messages;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    public MessageQ() {
        this.messages = new ArrayList<>();
        this.printWriter = null;
        this.bufferedReader = null;
    }

    public synchronized void addMessage(Message message) {
        if (!message.getMessage().contains("%exit"))
            messages.add(message);

        if (printWriter != null) {
            printWriter.println(message.getMessage());
        }
    }

    public synchronized List<Message> getMessages() {
        return new ArrayList<>(messages);
    }

    public synchronized void setMqForSendingMessages() {
        PipedInputStream pipedInputStream = new PipedInputStream();
        PipedOutputStream pipedOutputStream = new PipedOutputStream();

        try {
            pipedInputStream.connect(pipedOutputStream);
            this.bufferedReader = new BufferedReader(new InputStreamReader(pipedInputStream));
            this.printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(pipedOutputStream)), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void closePipe() {
        if (this.bufferedReader != null) {
            try {
                this.bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.bufferedReader = null;
        }
        if (this.printWriter != null) {
            this.printWriter.close();
            this.printWriter = null;
        }
    }

    public synchronized boolean hasPipeOpen() {
        return this.printWriter != null;
    }

    public synchronized BufferedReader getBufferedReader() {
        return this.bufferedReader;
    }

    public synchronized void clearMessages() {
        this.messages.clear();
    }

    public synchronized void sendOldMessagesThroughPrintWriter() {
        if (hasPipeOpen()) {
            for (Message message : messages) {
                printWriter.println(message.getMessage());
            }
        }
    }
}