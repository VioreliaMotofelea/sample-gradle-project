package com.example.calculator.operations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMinOperation {
    private Operation minOperation;

    @BeforeEach
    public void setup() {
        minOperation = new MinOperation();
    }

    @Test
    public void testMinPositiveNumbers() {
        assertEquals(2.0, minOperation.calculate(4.0, 2.0), 0.00001);
    }

    @Test
    public void testMinNegativeNumbers() {
        assertEquals(-4.0, minOperation.calculate(-2.0, -4.0), 0.00001);
    }

    @Test
    public void testMinPositiveAndNegativeNumbers() {
        assertEquals(-2.0, minOperation.calculate(2.0, -2.0), 0.00001);
    }

    @Test
    public void testMinZero() {
        assertEquals(0.0, minOperation.calculate(0.0, 2.0), 0.00001);
    }

    @Test
    public void testMinNumberToPlusInfinity() {
        assertEquals(5.0, minOperation.calculate(5, Double.POSITIVE_INFINITY), 0.00001);
    }

    @Test
    public void testMinNumberToMinusInfinity() {
        assertEquals(Double.NEGATIVE_INFINITY, minOperation.calculate(Double.NEGATIVE_INFINITY, 5), 0.00001);
    }

    @Test
    public void testMinNumberToMaxValue() {
        assertEquals(5.0, minOperation.calculate(5, Double.MAX_VALUE), 0.00001);
    }

    @Test
    public void testMinNumberToMinValue() {
        assertEquals(Double.MIN_VALUE, minOperation.calculate(Double.MIN_VALUE, 5), 0.00001);
    }

    @Test
    public void testMinToString() {
        assertEquals("Min", minOperation.toString());
    }
}
