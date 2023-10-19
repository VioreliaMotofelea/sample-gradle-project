package labs.lab1.model.operations.impl;

public class Subtraction extends AbstractOperation {

    public Subtraction(double... operators) {
        super(2, 2, operators);
    }

    @Override
    public Double calculate() {
        return operators.get(0) - operators.get(1);
    }
}
