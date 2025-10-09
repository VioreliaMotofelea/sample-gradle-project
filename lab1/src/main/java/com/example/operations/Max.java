package com.example.operations;

public class Max implements Operation {
    @Override
    public double execute(double... operands) {
        return Math.max(operands[0], operands[1]);
    }


}
