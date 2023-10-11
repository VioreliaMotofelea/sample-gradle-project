package org.example.domain;

public abstract class BinaryOperation<T> extends Operation<T> {
    T term1;

    T term2;

    public BinaryOperation (T t1, T t2){
        this.term1 = t1;
        this.term2 = t2;
    }
}
