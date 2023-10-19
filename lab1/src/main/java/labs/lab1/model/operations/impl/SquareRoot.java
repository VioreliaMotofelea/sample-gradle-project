package labs.lab1.model.operations.impl;

import labs.lab1.model.operations.IOperation;

public class SquareRoot implements IOperation {
    private final Double x;

    public SquareRoot(Double... operators) {
        assert operators.length == 1;

        this.x = operators[0];
    }

    @Override
    public Double calculate() {
        return Math.sqrt(x);
    }
}
