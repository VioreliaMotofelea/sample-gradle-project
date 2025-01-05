package com.example.calculator.operations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMultiplication {
    private Operation multiplicationOperation;

    @BeforeEach
    public void setup() {
        multiplicationOperation = new MultiplicationOperation();
    }

    @Test
    public void testMultiplyPositiveNumbers() {
        assertEquals(4.0, multiplicationOperation.calculate(2.0, 2.0), 0.00001);
    }

    @Test
    public void testMultiplyNegativeNumbers() {
        assertEquals(4.0, multiplicationOperation.calculate(-2.0, -2.0), 0.00001);
    }

    @Test
    public void testMultiplyPositiveAndNegativeNumbers() {
        assertEquals(-4.0, multiplicationOperation.calculate(2.0, -2.0), 0.00001);
    }

    @Test
    public void testMultiplyZero() {
        assertEquals(0.0, multiplicationOperation.calculate(0.0, 2.0), 0.00001);
    }

    @Test
    public void testMultiplyTwoZeros() {
        assertEquals(0.0, multiplicationOperation.calculate(0.0, 0.0), 0.00001);
    }

    @Test
    public void testMultiplicationToString() {
        assertEquals("Multiplication", multiplicationOperation.toString());
    }
}
