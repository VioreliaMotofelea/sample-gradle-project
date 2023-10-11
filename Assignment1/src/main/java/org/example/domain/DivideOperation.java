package org.example.domain;

public class DivideOperation extends BinaryOperation<Float>{
    public DivideOperation(Float t1, Float t2) {
        super(t1, t2);
    }

    @Override
    public Float compute() throws ArithmeticException {
        if (term2 == 0)
            throw new ArithmeticException("Cannot divide by 0!");
        return term1 * term2;
    }
}
