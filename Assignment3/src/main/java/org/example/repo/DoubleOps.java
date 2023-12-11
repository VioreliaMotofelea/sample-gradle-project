package org.example.repo;

import java.util.Comparator;
import java.util.List;

public class DoubleOps extends NumberOps<Double> {

    public DoubleOps(List<Double> list) {
        super(list);
    }

    @Override
    protected Double zero() {
        return 0.0;
    }

    @Override
    protected Double add(Double a, Double b) {
        return a + b;
    }

    @Override
    protected Double divide(Double a, int b) {
        return a / b;
    }

    @Override
    protected Comparator<? super Double> comparator() {
        return Comparator.reverseOrder();
    }
}
