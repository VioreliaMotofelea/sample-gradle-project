package org.example.domain;

public class MinOperation extends BinaryOperation<Float>{
    public MinOperation(Float t1, Float t2) {
        super(t1, t2);
    }

    @Override
    public Float compute() throws ArithmeticException {
        return Math.min(term1, term2);
    }
}
