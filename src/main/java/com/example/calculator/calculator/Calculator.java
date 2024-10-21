package com.example.calculator.calculator;

import com.example.calculator.operations.*;

import java.util.HashMap;
import java.util.Map;

public class Calculator implements CalculatorInterface {
    private final Map<String, Operation> operations;

    public Calculator() {
        this.operations = new HashMap<>();
        this.operations.put("+", new AddOperation());
        this.operations.put("-", new SubtractOperation());
        this.operations.put("*", new MultiplicationOperation());
        this.operations.put("/", new DivideOperation());
        this.operations.put("min", new MinOperation());
        this.operations.put("max", new MaxOperation());
        this.operations.put("sqrt", new SqrtOperation());
    }

    public Calculator(Map<String, Operation> operations) {
        this.operations = operations;
    }

    @Override
    public double calculate(double a, double b, String operator) {
        Operation operation = this.operations.get(operator);
        if (operation == null) {
            throw new IllegalArgumentException("Invalid operator");
        }
        return operation.calculate(a, b);
    }

}
