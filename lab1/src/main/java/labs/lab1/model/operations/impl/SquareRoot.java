package labs.lab1.model.operations.impl;

public class SquareRoot extends AbstractOperation {

    public SquareRoot(double... operators) {
        super(1, 1, operators);
    }

    @Override
    public Double calculate() {
        return Math.sqrt(operators.get(0));
    }
}
