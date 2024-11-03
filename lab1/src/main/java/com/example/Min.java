package com.example;

public class Min implements Operation {
  @Override
  public double calculate(double a, double b) {
    return Math.min(a, b);
  }
}
