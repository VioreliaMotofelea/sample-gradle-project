package org.example.domain;

public class AddOperation extends BinaryOperation<Float>{

    public AddOperation(Float t1, Float t2) {
        super(t1, t2);
    }

    @Override
    public Float compute() {
        return term1 + term2;
    }
}
