package com.example.calculator;

import com.example.calculator.calculator.Calculator;
import com.example.calculator.calculator.CalculatorInterface;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CalculatorInterface calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter Operations: +, -, *, /, max, min, sqrt, exit.");
            String operator = scanner.nextLine();
            if (operator.equals("exit")) {
                break;
            }
            System.out.println("a: ");
            double a = scanner.nextDouble();
            double b = 0;
            if (!operator.equals("sqrt")) {
                System.out.println("b: ");
                b = scanner.nextDouble();
            }
            scanner.nextLine();
            try {
                double result = calculator.calculate(a, b, operator);
                System.out.println("Result: " + result);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
