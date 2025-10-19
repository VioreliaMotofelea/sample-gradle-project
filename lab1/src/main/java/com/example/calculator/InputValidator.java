package com.example.calculator;


import java.util.Arrays;
import java.util.List;

public class InputValidator {

    private static final List<String> UNARY_OPERATORS = List.of(Operator.SQRT.symbol);


    public List<String> validate(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty.");
        }

        String[] parts = input.trim().split("\\s+");

        if (parts.length < 2 || parts.length > 3) {
            throw new IllegalArgumentException("Invalid input format. Use 'number op number' or 'op number'.");
        }

        if (parts.length == 2) {
            String op = parts[0].trim().toLowerCase();
            if (!UNARY_OPERATORS.contains(op) || Operator.fromSymbol(op).isEmpty()) {
                throw new IllegalArgumentException("Invalid format for operator '" + op + "'. Use 'op number'.");
            }
            validateNumber(parts[1]);
        }

        if (parts.length == 3) {
            validateNumber(parts[0]);
            Operator.fromSymbol(parts[1])
                    .orElseThrow(() -> new IllegalArgumentException("Unknown operator: " + parts[1]));
            validateNumber(parts[2]);
        }

        return Arrays.asList(parts);
    }

    private void validateNumber(String part) {
        try {
            Double.parseDouble(part);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number: " + part);
        }
    }
}

