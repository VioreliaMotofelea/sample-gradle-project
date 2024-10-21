package com.example.calculator.operations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestSqrtOperation {
    private Operation sqrtOperation;

    @BeforeEach
    public void setup() {
        sqrtOperation = new SqrtOperation();
    }

    @Test
    public void testSqrtPositiveInteger() {
        assertEquals(2.0, sqrtOperation.calculate(4.0, 0), 0.00001);
    }

    @Test
    public void testSqrtPositiveDecimal() {
        assertEquals(1.5, sqrtOperation.calculate(2.25, 0), 0.00001);
    }

    @Test
    public void testSqrtPositiveIrrationalResult() {
        assertEquals(1.414213562, sqrtOperation.calculate(2.0, 0), 0.00001);
    }

    @Test
    public void testSqrtNegativeNumber() {
        assertThrows(IllegalArgumentException.class, () -> sqrtOperation.calculate(-4.0, 0));
    }

    @Test
    public void testSqrtToString() {
        assertEquals("Square Root", sqrtOperation.toString());
    }
}
