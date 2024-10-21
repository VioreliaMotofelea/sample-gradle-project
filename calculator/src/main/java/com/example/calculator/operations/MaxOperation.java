package com.example.calculator.operations;

public class MaxOperation implements Operation{
    @Override
    public double calculate(double a, double b) {
        return Math.max(a, b);
    }

    @Override
    public String toString() {
        return "Max";
    }
}
