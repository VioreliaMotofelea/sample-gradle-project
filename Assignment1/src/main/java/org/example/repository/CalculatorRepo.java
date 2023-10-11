package org.example.repository;

import org.example.domain.Operation;

public class CalculatorRepo<T> {
    private T currentResult;

    public CalculatorRepo() {
        currentResult = null;
    }

    public T performOperation(Operation<T> operation) {
        currentResult = operation.compute();
        return currentResult;
    }

    public T getCurrentResult() {
        return currentResult;
    }

    public void setCurrentResult(T currentResult) {
        this.currentResult = currentResult;
    }
}
