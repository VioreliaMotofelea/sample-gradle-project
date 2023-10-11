package org.example.ui;

import org.example.controller.CalculatorController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdLineUI {
    private static CmdLineUI INSTANCE = null;
    private final CalculatorController controller;

    private BufferedReader reader;

    private CmdLineUI() {
        controller = new CalculatorController();
        reader = new BufferedReader(
                new InputStreamReader(System.in));
    }

    public void start() {
        while (true) {
            try {
                displayOneStep();
            } catch (IOException e) {
                System.out.println("An error occured: " + e.getMessage());
            }
        }
    }

    public static synchronized CmdLineUI getInstance() {
        if (INSTANCE == null)
            INSTANCE = new CmdLineUI();
        return INSTANCE;
    };

    private void displayOneStep() throws IOException {
        System.out.println("Choose an operation (+, -, /, *, min, max, sqrt, clear): ");

        String operation = readOperation();
        if (operation.equals("clear")) {
            controller.clearResult();
            displayResult(0f);
            return;
        }

        Float nr1, nr2;

        if (controller.hasPreviousResult()) {
            nr1 = controller.getPreviousResult();
        } else {
            nr1 = readNumber();
        }

        nr2 = readNumber();

        Float result = computeOperation(operation, nr1, nr2);
        displayResult(result);
    }

    private void displayResult(Float result) {
        System.out.println("The result is: " + result);
    }

    private Float computeOperation(String operation, Float nr1, Float nr2) {
        switch (operation) {
            case "+":
                return controller.performAddOperation(nr1, nr2);
            default:
                throw new RuntimeException("Could not perform operation");
        }
    }

    private String readOperation() throws IOException {
        try {
            String op = reader.readLine().trim();
            assert controller.isAllowedOperation(op);
            return op;
        } catch (IOException e) {
            throw new IOException(e);
        } catch (Exception e) {
            throw new RuntimeException("Not a permitted operation!");
        }
    }

    private Float readNumber() {
        System.out.println("Write number: ");

        try {
            return Float.parseFloat(reader.readLine().trim());
        } catch (Exception e) {
            throw new RuntimeException("Not a float!");
        }
    }
}
