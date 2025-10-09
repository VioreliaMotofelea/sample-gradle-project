package com.example.operations;


public class Min implements Operation {
    @Override
    public double execute(double... operands) {
        return Math.min(operands[0], operands[1]);
    }


}
