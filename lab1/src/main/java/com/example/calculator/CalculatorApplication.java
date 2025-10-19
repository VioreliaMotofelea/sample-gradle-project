package com.example.calculator;

import java.util.List;
import java.util.Scanner;

public class CalculatorApplication {
    public static void main(String[] args) {
        CalculatorApplication app = new CalculatorApplication();
        app.run();
    }

    private final MathCalculator calculator;
    private final InputValidator validator;
    private final Scanner scanner;

    public CalculatorApplication() {
        this.calculator = new MathCalculator();
        this.validator = new InputValidator();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        printWelcomeMessage();
        mainLoop();
        scanner.close();
    }

    private void mainLoop() {
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input.trim())) {
                break;
            }

            if (input.trim().isEmpty()) {
                continue;
            }

            try {
                List<String> parts = validator.validate(input);
                processCalculation(parts);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }

    private void processCalculation(List<String> parts) {
        String operatorSymbol = parts.size() == 2 ? parts.get(0) : parts.get(1);
        Operator op = Operator.fromSymbol(operatorSymbol)
                .orElseThrow(() -> new IllegalStateException("Operator " + operatorSymbol + " not valid"));

        double result;

        if (op == Operator.SQRT) {
            double num = Double.parseDouble(parts.get(1));
            result = calculator.sqrt(num);
        } else {
            double num1 = Double.parseDouble(parts.get(0));
            double num2 = Double.parseDouble(parts.get(2));

            switch (op) {
                case ADD:
                    result = calculator.add(num1, num2);
                    break;
                case SUBTRACT:
                    result = calculator.subtract(num1, num2);
                    break;
                case MULTIPLY:
                    result = calculator.multiply(num1, num2);
                    break;
                case DIVIDE:
                    result = calculator.divide(num1, num2);
                    break;
                case MIN:
                    result = calculator.min(num1, num2);
                    break;
                case MAX:
                    result = calculator.max(num1, num2);
                    break;
                default:
                    throw new IllegalStateException("Unsupported operator: " + op);
            }
        }
        System.out.println("Result: " + result);
    }


    private void printWelcomeMessage() {
        System.out.println("--- CLI Calculator ---");
        System.out.println("Enter calculations in the format: 'number operator number' (e.g., 5 + 3)");
        System.out.println("For square root, use: 'sqrt number' (e.g., sqrt 9)");
        System.out.println("Supported operators: +, -, *, /, min, max, sqrt");
        System.out.println("Enter 'exit' to quit.");
        System.out.println("---------------------------");
    }
}


