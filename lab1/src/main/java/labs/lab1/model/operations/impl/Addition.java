package labs.lab1.model.operations.impl;

public class Addition extends AbstractOperation {

    public Addition(double... operators) {
        super(1, operators);
    }

    @Override
    public Double calculate() {
        return operators.stream()
                .reduce(0.0, Double::sum);
    }
}
