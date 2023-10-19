package labs.lab1.model.operations.impl;

public class Minimum extends AbstractOperation {

    public Minimum(double... operators) {
        super(1, operators);
    }

    @Override
    public Double calculate() {
        return operators.stream()
                .min(Double::compare)
                .orElseThrow();
    }
}
