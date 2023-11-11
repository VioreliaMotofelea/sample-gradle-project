package org.example.repo;

public class AddOperation extends BinaryOperation<Float>{
    public AddOperation(Float t1, Float t2) {
        super(t1, t2);
    }

    @Override
    public Float compute() throws ArithmeticException {
        return term1 + term2;
    }
}
