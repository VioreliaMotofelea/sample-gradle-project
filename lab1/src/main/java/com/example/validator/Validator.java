package com.example.validator;

import com.example.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Validator {

    public void validate(String[] tokens) throws ValidationException {
        List<String> errors = new ArrayList<>();

        if (tokens == null || tokens.length < 3) {
            errors.add("Invalid expression: must have at least two operands and one operator.");
        }

        int i = 0;
        boolean expectOperand = true;

        while (i < tokens.length) {
            String token = tokens[i];

            if (expectOperand) {
                //min max
                if (token.equals("min") || token.equals("max")) {
                    if (i + 2 >= tokens.length ||
                            !Utils.isNumber(tokens[i + 1]) ||
                            !Utils.isNumber(tokens[i + 2])) {
                        errors.add("Invalid usage of " + token);
                        break;
                    }
                    i += 3; // jump over min/max a b
                    expectOperand = false; // now expected an operator
                    continue;
                }

                //operand
                if (!Utils.isNumber(token)) {
                    errors.add("Expected a number but found: " + token);
                    break;
                }

                expectOperand = false;
            } else {
               //normal operator
                if (!Utils.isOperator(token)) {
                    errors.add("Expected an operator but found: " + token);
                    break;
                }
                expectOperand = true;
            }

            i++;
        }

        if (expectOperand) {
            errors.add("Expression cannot end with an operator.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
