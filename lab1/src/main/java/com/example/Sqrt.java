package com.example;

public class Sqrt implements Operation {
  @Override
  public double calculate(double a, double b) {
    return Math.pow(a, 1/b);
  }

  public double calculate(double a) {
    if(a < 0){
      throw new IllegalArgumentException("Cannot calculate the square root of a negative number");
    }
    return Math.sqrt(a);
  }
}
