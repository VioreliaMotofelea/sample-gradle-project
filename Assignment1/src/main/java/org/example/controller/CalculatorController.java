package org.example.controller;

import org.example.domain.AddOperation;
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
}
