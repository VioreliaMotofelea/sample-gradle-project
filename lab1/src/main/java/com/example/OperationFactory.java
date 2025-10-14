package com.example;

import com.example.operations.*;

public class OperationFactory {

    public static Operation getOp(String symbol) {
        switch (symbol) {
            case "+": return new Addition();
            case "-": return new Substraction();
            case "*": return new Multiplication();
            case "/": return new Division();
            case "min": return new Min();
            case "max": return new Max();
            default: throw new IllegalArgumentException("Unknown operation: " + symbol);
        }
    }
}
