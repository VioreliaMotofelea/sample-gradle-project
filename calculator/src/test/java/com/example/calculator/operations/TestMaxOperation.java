package com.example.calculator.operations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMaxOperation {
    private Operation maxOperation;

    @BeforeEach
    public void setup() {
        maxOperation = new MaxOperation();
    }

    @Test
    public void testMaxPositiveNumbers() {
        assertEquals(4.0, maxOperation.calculate(2.0, 4.0), 0.00001);
    }

    @Test
    public void testMaxNegativeNumbers() {
        assertEquals(-2.0, maxOperation.calculate(-2.0, -4.0), 0.00001);
    }

    @Test
    public void testMaxPositiveAndNegativeNumbers() {
        assertEquals(2.0, maxOperation.calculate(2.0, -2.0), 0.00001);
    }

    @Test
    public void testMaxZero() {
        assertEquals(2.0, maxOperation.calculate(0.0, 2.0), 0.00001);
    }

    @Test
    public void testMaxNumberToPlusInfinity() {
        assertEquals(Double.POSITIVE_INFINITY, maxOperation.calculate(5, Double.POSITIVE_INFINITY), 0.00001);
    }

    @Test
    public void testMaxNumberToMinusInfinity() {
        assertEquals(5.0, maxOperation.calculate(Double.NEGATIVE_INFINITY, 5), 0.00001);
    }

    @Test
    public void testMaxNumberToMaxValue() {
        assertEquals(Double.MAX_VALUE, maxOperation.calculate(5, Double.MAX_VALUE), 0.00001);
    }

    @Test
    public void testMaxNumberToMinValue() {
        assertEquals(5.0, maxOperation.calculate(Double.MIN_VALUE, 5), 0.00001);
    }
}
