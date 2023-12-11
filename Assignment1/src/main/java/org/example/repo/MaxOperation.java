package org.example.repo;

public class MaxOperation extends BinaryOperation<Float>{
    public MaxOperation(Float t1, Float t2) {
        super(t1, t2);
    }

    @Override
    public Float compute() throws ArithmeticException {
        return Math.max(term1, term2);
    }
}
