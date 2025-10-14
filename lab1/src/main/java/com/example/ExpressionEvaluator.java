package com.example;

import com.example.operations.Operation;
import com.example.validator.ValidationException;
import com.example.validator.Validator;

import java.util.Map;
import java.util.Stack;

public class ExpressionEvaluator {

    private static final Map<String, Integer> precedence = Map.of(
            "+", 1, "-", 1,
            "*", 2, "/", 2,
            "min", 3, "max", 3
    );

    private final Validator validator;

    public ExpressionEvaluator(Validator validator) {
        this.validator = validator;
    }

    public double evaluate(String expr) throws ValidationException {
        String[] tokens = expr.trim().split("\\s+");

        validator.validate(tokens);

        Stack<Double> values = new Stack<>();
        Stack<String> ops = new Stack<>();

        for (String token : tokens) {
            if (Utils.isNumber(token)) {
                values.push(Double.parseDouble(token));
            } else if (Utils.isOperator(token)) {
                while (!ops.isEmpty() && precedence.getOrDefault(token, 0) <= precedence.getOrDefault(ops.peek(), 0)) {
                    applyOperator(values, ops);
                }
                ops.push(token);
            } else {
                throw new IllegalArgumentException("Invalid argument: " + token);
            }
        }

        while (!ops.isEmpty()) {
            applyOperator(values, ops);
        }

        return values.pop();
    }

    private void applyOperator(Stack<Double> values, Stack<String> ops) {
        double b = values.pop();
        double a = values.pop();
        String opSymbol = ops.pop();
        Operation op = OperationFactory.getOp(opSymbol);
        values.push(op.execute(a, b));
    }
}
