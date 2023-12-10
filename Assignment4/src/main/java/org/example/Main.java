package org.example;

public class Main {
    public static void main(String[] args) {
//        new Server(5000).start();
        new Client("localhost", 5000).start();
    }
}