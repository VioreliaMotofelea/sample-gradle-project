package org.example.repo;

public class MultiplyOperation extends BinaryOperation<Float>{
    public MultiplyOperation(Float t1, Float t2) {
        super(t1, t2);
    }

    @Override
    public Float compute() throws ArithmeticException {
        return term1 * term2;
    }
}
