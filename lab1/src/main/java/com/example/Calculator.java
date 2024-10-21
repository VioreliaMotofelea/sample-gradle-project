package com.example;

import java.util.HashMap;
import java.util.Map;

public class Calculator {
  private final Map<String, Operation> operations = new HashMap<>();

  public Calculator() {
    operations.put("+", new Addition());
    operations.put("-", new Subtraction());
    operations.put("*", new Multiplication());
    operations.put("/", new Division());
    operations.put("max", new Max());
    operations.put("min", new Min());
  }

  public double calculate(String operator, double a, double b) {
    Operation operation = operations.get(operator);
    if (operation == null) {
      throw new UnsupportedOperationException("Operation not supported");
    }
    return operation.calculate(a, b);
  }
}
