package org.example.UI;

import org.example.Domain.Operation;

import java.io.Console;

public class UI {

    private final Console console = System.console();
    public void runUI(){
        System.out.println("Please input operation delimited by spaces\nNote min and max support only 2 operands\nex: sqrt 4 + 2 * min 2 3");
        String line = console.readLine();

    }
}
