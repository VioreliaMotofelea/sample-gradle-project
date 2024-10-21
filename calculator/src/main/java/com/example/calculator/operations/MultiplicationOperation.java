package com.example.calculator.operations;

public class MultiplicationOperation implements Operation{
    @Override
    public double calculate(double a, double b) {
        return a * b;
    }

    @Override
    public String toString() {
        return "Multiplication";
    }
}
