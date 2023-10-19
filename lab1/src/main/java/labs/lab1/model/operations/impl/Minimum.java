package labs.lab1.model.operations.impl;

import labs.lab1.model.operations.IOperation;

import java.util.ArrayList;
import java.util.Arrays;

public class Minimum implements IOperation {
    private final ArrayList<Double> x;

    public Minimum(Double... operators) {
        if(operators.length == 0) {
            throw new IllegalArgumentException("No numbers provided");
        }

        this.x = new ArrayList<>();
        this.x.addAll(Arrays.asList(operators));
    }

    @Override
    public Double calculate() {
        return x.stream()
                .min(Double::compare)
                .orElseThrow(IllegalArgumentException::new);
    }
}
