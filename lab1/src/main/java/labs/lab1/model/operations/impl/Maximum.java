package labs.lab1.model.operations.impl;

import labs.lab1.model.operations.IOperation;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;

public class Maximum implements IOperation {
    private final ArrayList<Double> x;

    public Maximum(Double... operators) {
        if(operators.length == 0) {
            throw new InvalidParameterException("No numbers provided");
        }

        this.x = new ArrayList<>();
        this.x.addAll(Arrays.asList(operators));
    }

    @Override
    public Double calculate() {
        return x.stream()
                .max(Double::compare)
                .orElseThrow(InvalidParameterException::new);
    }
}
