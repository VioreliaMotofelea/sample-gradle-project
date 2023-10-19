package labs.lab1.model.operations.impl;

public class Maximum extends AbstractOperation {

    public Maximum(double... operators) {
        super(1, operators);
    }

    @Override
    public Double calculate() {
        return operators.stream()
                .max(Double::compare)
                .orElseThrow();
    }
}
