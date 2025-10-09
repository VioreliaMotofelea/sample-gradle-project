package com.example.validator;

import com.example.Utils;
import java.util.ArrayList;
import java.util.List;

public class Validator {

    public void validate(String[] tokens) throws ValidationException {
        List<String> errors = new ArrayList<>();

        if (!hasValidLength(tokens, errors)) {
            throwIfErrors(errors);
            return;
        }

        int i = 0;
        boolean expectOperand = true;

        while (i < tokens.length) {
            String token = tokens[i];

            if (expectOperand) {
                i = validateOperand(tokens, i, errors);
                expectOperand = false;
            } else {
                i = validateOperator(tokens, i, errors);
                expectOperand = true;
            }

            if (!errors.isEmpty()) break;
        }

        if (expectOperand) {
            errors.add("Expression cannot end with an operator.");
        }

        throwIfErrors(errors);
    }

    private boolean hasValidLength(String[] tokens, List<String> errors) {
        if (tokens == null || tokens.length < 3) {
            errors.add("Invalid expression: must have at least two operands and one operator.");
            return false;
        }
        return true;
    }

    private int validateOperand(String[] tokens, int index, List<String> errors) {
        String token = tokens[index];

        if (isMinOrMax(token)) {
            return validateMinMax(tokens, index, errors);
        }

        if (!Utils.isNumber(token)) {
            errors.add("Expected a number but found: " + token);
        }

        return index + 1;
    }

    private int validateMinMax(String[] tokens, int index, List<String> errors) {
        String token = tokens[index];
        if (index + 2 >= tokens.length ||
                !Utils.isNumber(tokens[index + 1]) ||
                !Utils.isNumber(tokens[index + 2])) {
            errors.add("Invalid usage of " + token);
            return tokens.length; // stop further checks
        }
        return index + 3;
    }

    private int validateOperator(String[] tokens, int index, List<String> errors) {
        String token = tokens[index];
        if (!Utils.isOperator(token)) {
            errors.add("Expected an operator but found: " + token);
        }
        return index + 1;
    }

    private boolean isMinOrMax(String token) {
        return "min".equals(token) || "max".equals(token);
    }

    private void throwIfErrors(List<String> errors) throws ValidationException {
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
