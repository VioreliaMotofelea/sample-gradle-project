package labs.lab1.model.operations.impl;

import labs.lab1.model.operations.IOperation;

public class Subtraction implements IOperation {
    private final Double x1, x2;

    public Subtraction(Double... operators) {
        assert operators.length == 2;

        this.x1 = operators[0];
        this.x2 = operators[1];
    }

    @Override
    public Double calculate() {
        return x1 - x2;
    }
}
