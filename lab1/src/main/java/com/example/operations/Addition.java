package com.example.operations;

public class Addition implements Operation {

    @Override
    public double execute(double... operands) {
        return operands[0] + operands[1];
    }


}
