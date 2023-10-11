package org.example.domain;

public abstract class Operation<T> {
     abstract public T compute() throws ArithmeticException;
}
