package com.example;

import java.util.Scanner;

public class CalculatorUI {
  private final Parser parser;
  private final Evaluator evaluator;

  private final Scanner scanner;

  public CalculatorUI(Parser parser, Evaluator evaluator, Scanner scanner) {
    this.parser = parser;
    this.evaluator = evaluator;
    this.scanner = scanner;
  }

  public void run() {
    while(true) {
      printWelcomeString();

      System.out.println("Enter the expression:");
      System.out.print("> ");
      String line = scanner.nextLine().trim();

      if("exit".equalsIgnoreCase(line)) {
        break;
      }

      try {
        BinaryExpression expression = parser.parse(line);
        int result = evaluator.evaluate(expression);
        System.out.println("Result: " + result);
      } catch(Exception ex) {
        System.out.println("Error: " + ex.getMessage());
      }
    }
  }

  private void printWelcomeString() {
    System.out.println("Welcome to my cli calculator!\n" +
            "Enter the expression: a+b | a-b | a*b | a/b\n" +
            "        or exit if you want to close the application\n\n");
  }
}
