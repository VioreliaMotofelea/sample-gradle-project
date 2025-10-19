package com.example.calculator;

import java.util.Arrays;
import java.util.Optional;

public enum Operator {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    MIN("min"),
    MAX("max"),
    SQRT("sqrt");

    public final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public static Optional<Operator> fromSymbol(String symbol) {
        return Arrays.stream(values())
                .filter(op -> op.symbol.equalsIgnoreCase(symbol))
                .findFirst();
    }
}
