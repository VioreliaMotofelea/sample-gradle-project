package com.example.calculator.operations;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSubtractOperation {
    @Test
    void subtractsTwoPositiveNumbers() {
        SubtractOperation operation = new SubtractOperation();
        assertEquals(5, operation.calculate(10, 5));
    }

    @Test
    void subtractsTwoNegativeNumbers() {
        SubtractOperation operation = new SubtractOperation();
        assertEquals(-5, operation.calculate(-10, -5));
    }

    @Test
    void subtractsPositiveAndNegativeNumber() {
        SubtractOperation operation = new SubtractOperation();
        assertEquals(15, operation.calculate(10, -5));
    }

    @Test
    void subtractsNegativeAndPositiveNumber() {
        SubtractOperation operation = new SubtractOperation();
        assertEquals(-15, operation.calculate(-10, 5));
    }

    @Test
    void subtractsZeroFromNumber() {
        SubtractOperation operation = new SubtractOperation();
        assertEquals(10, operation.calculate(10, 0));
    }

    @Test
    void subtractsNumberFromZero() {
        SubtractOperation operation = new SubtractOperation();
        assertEquals(-10, operation.calculate(0, 10));
    }

    @Test
    void subtractsTwoZeros() {
        SubtractOperation operation = new SubtractOperation();
        assertEquals(0, operation.calculate(0, 0));
    }
}
