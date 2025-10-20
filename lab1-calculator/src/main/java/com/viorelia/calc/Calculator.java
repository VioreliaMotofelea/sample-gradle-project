package com.viorelia.calc;

public class Calculator {
    public double add(double a, double b) { return a + b; }
    public double sub(double a, double b) { return a - b; }
    public double mul(double a, double b) { return a * b; }
    public double div(double a, double b) {
        if (b == 0) throw new ArithmeticException("Division by zero");
        return a / b;
    }
    public double min(double a, double b) { return Math.min(a, b); }
    public double max(double a, double b) { return Math.max(a, b); }
    public double sqrt(double x) {
        if (x < 0) throw new IllegalArgumentException("Negative input");
        return Math.sqrt(x);
    }
}
