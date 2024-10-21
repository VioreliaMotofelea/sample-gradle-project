package com.example.calculator.operations;

public class SqrtOperation implements Operation{
    @Override
    public double calculate(double a, double b) {
        if (a < 0) {
            throw new IllegalArgumentException("Square root of negative number is not a real number");
        }
        return Math.sqrt(a);
    }

    @Override
    public String toString() {
        return "Square Root";
    }
}
