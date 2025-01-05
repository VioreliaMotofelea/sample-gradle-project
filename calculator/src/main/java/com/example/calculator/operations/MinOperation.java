package com.example.calculator.operations;

public class MinOperation implements Operation{
    @Override
    public double calculate(double a, double b) {
        return Math.min(a, b);
    }

    @Override
    public String toString() {
        return "Min";
    }
}
