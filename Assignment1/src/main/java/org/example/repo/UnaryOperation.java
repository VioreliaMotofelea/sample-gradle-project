package org.example.repo;

public abstract class UnaryOperation<T> extends Operation<T> {
    T term;

    public UnaryOperation(T term) {
        this.term = term;
    }
}
