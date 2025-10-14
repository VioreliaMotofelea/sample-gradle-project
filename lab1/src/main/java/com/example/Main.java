package com.example;

import com.example.validator.Validator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Validator validator = new Validator();
        ExpressionEvaluator evaluator = new ExpressionEvaluator( validator);

        while (true) {
            System.out.println("------------------------------------------------------");
            System.out.println("Enter expression (e.g., 1 + 2 * 3), or 'exit' to quit:");
            String line = scanner.nextLine();

            if (line.equalsIgnoreCase("exit")) break;

            try {
                double result = evaluator.evaluate(line);
                System.out.println("Result: " + result);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        scanner.close();
    }
}
