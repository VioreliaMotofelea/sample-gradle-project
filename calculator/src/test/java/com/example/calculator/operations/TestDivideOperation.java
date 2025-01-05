package com.example.calculator.operations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestDivideOperation {
    private Operation divideOperation;

    @BeforeEach
    public void setup() {
        divideOperation = new DivideOperation();
    }

    @Test
    public void testDividePositiveNumbers() {
        assertEquals(2.0, divideOperation.calculate(4.0, 2.0), 0.00001);
    }

    @Test
    public void testDivideNegativeNumbers() {
        assertEquals(2.0, divideOperation.calculate(-4.0, -2.0), 0.00001);
    }

    @Test
    public void testDividePositiveByNegative() {
        assertEquals(-2.0, divideOperation.calculate(4.0, -2.0), 0.00001);
    }

    @Test
    public void testDivideNegativeByPositive() {
        assertEquals(-2.0, divideOperation.calculate(-4.0, 2.0), 0.00001);
    }

    @Test
    public void testDivideZero() {
        assertEquals(0.0, divideOperation.calculate(0.0, 2.0), 0.00001);
    }

    @Test
    public void testDivideByZero() {
        assertThrows(IllegalArgumentException.class, () -> divideOperation.calculate(2.0, 0.0));
    }

    @Test
    public void testDivideToString() {
        assertEquals("Division", divideOperation.toString());
    }
}
