package org.example.domain;

public class DeleteOperation extends BinaryOperation<Float>{
    public DeleteOperation(Float t1, Float t2) {
        super(t1, t2);
    }

    @Override
    public Float compute() throws ArithmeticException {
        return term1 - term2;
    }
}
