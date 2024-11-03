package com.example;

public class Max implements Operation {
  @Override
  public double calculate(double a, double b) {
    return Math.max(a, b);
  }
}
