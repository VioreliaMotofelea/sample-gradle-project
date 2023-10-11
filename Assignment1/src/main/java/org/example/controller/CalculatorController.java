package org.example.controller;

import org.example.domain.*;
import org.example.repository.CalculatorRepo;

import java.util.ArrayList;
import java.util.List;

public class CalculatorController {
    private final CalculatorRepo<Float> calculatorRepo;
    private final List<String> permittedOpreations;

    public CalculatorController() {
        calculatorRepo = new CalculatorRepo<>();
        permittedOpreations = new ArrayList<>();
        permittedOpreations.addAll(List.of("+", "-", "*", "/", "min", "max", "sqrt"));
    }

    public boolean isAllowedOperation(String op) {
        return permittedOpreations.contains(op.trim());
    }

    public boolean hasPreviousResult() {
        return calculatorRepo.getCurrentResult() != null;
    }

    public Float getPreviousResult() {
        return calculatorRepo.getCurrentResult();
    }

    public void clearResult() {
        calculatorRepo.setCurrentResult(null);
    }

    public Float performAddOperation(Float a, Float b) {
        return calculatorRepo.performOperation(new AddOperation(a, b));
    }

    public Float performDeleteOperation(Float nr1, Float nr2) {
        return calculatorRepo.performOperation(new DeleteOperation(nr1, nr2));
    }

    public Float performMultiplyOperation(Float nr1, Float nr2) {
        return calculatorRepo.performOperation(new MultiplyOperation(nr1, nr2));
    }

    public Float performDivideOperation(Float nr1, Float nr2) {
        return calculatorRepo.performOperation(new DivideOperation(nr1, nr2));
    }

    public Float performMinOperation(Float nr1, Float nr2) {
        return calculatorRepo.performOperation(new MinOperation(nr1, nr2));
    }

    public Float performMaxOperation(Float nr1, Float nr2) {
        return calculatorRepo.performOperation(new MaxOperation(nr1, nr2));
    }

    public Float performSqrtOperation(Float nr) {
        return calculatorRepo.performOperation(new SqrtOperation(nr));
    }
}
