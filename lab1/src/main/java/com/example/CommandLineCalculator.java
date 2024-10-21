package com.example;
import java.util.Scanner;

public class CommandLineCalculator {

  public static void main(String[] args) {
    Calculator calculator = new Calculator();
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter an operation(eg: 5 + 3 ): ");

    while(scanner.hasNext()) {
      double a = scanner.nextDouble();
      String operator = scanner.next();
      double b = scanner.nextDouble();

      try {
        double result = calculator.calculate(operator, a, b);
        System.out.println("Result: " + result);
      } catch (RuntimeException e) {
        System.out.println();
      }
    }
  }
}
