package labs.lab1.model.operations.impl;

public class Multiplication extends AbstractOperation {

    public Multiplication(double... operators) {
        super(1, operators);
    }

    @Override
    public Double calculate() {
        return operators.stream()
                .reduce(1.0, (a, b) -> a * b);
    }
}
