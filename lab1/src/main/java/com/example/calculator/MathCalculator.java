package com.example.calculator;

public class MathCalculator extends Calculator {

    public double min(double a, double b) {
        return Math.min(a, b);
    }

    public double max(double a, double b) {
        return Math.max(a, b);
    }

    public double sqrt(double a) {
        if (a < 0) {
            throw new IllegalArgumentException("Cannot calculate the square root of a negative number.");
        }
        return Math.sqrt(a);
    }
}

