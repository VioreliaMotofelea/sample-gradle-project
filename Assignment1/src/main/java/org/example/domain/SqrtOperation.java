package org.example.domain;

public class SqrtOperation extends UnaryOperation<Float> {
    public SqrtOperation(Float term) {
        super(term);
    }

    @Override
    public Float compute() throws ArithmeticException {
        if (term < 0)
            throw new ArithmeticException("Cannot perform sqrt on a number <0!");
        Double res = Math.sqrt(term);
        return res.floatValue();
    }
}
