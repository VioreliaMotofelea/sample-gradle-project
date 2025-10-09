package com.example.operations;

public class Division implements Operation {
    @Override
    public double execute(double... operands) {
        if (operands[1] == 0)
            throw new ArithmeticException("Cannot divide by zero");
        return operands[0] / operands[1];
    }


}