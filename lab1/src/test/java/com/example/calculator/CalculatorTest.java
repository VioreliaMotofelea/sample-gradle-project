package com.example.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    void testAdd() {
        assertEquals(5.0, calculator.add(2.0, 3.0));
        assertEquals(-1.0, calculator.add(-2.0, 1.0));
        assertEquals(0.0, calculator.add(0.0, 0.0));
    }

    @Test
    void testSubtract() {
        assertEquals(-1.0, calculator.subtract(2.0, 3.0));
        assertEquals(5.5, calculator.subtract(10.0, 4.5));
        assertEquals(0.0, calculator.subtract(-5.0, -5.0));
    }

    @Test
    void testMultiply() {
        assertEquals(6.0, calculator.multiply(2.0, 3.0));
        assertEquals(-10.0, calculator.multiply(5.0, -2.0));
        assertEquals(0.0, calculator.multiply(100.0, 0.0));
    }

    @Test
    void testDivide() {
        assertEquals(2.0, calculator.divide(6.0, 3.0));
        assertEquals(-2.5, calculator.divide(5.0, -2.0));
        assertEquals(4.0, calculator.divide(10.0, 2.5));
    }

    @Test
    void testDivideByZeroThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.divide(10.0, 0.0)
        );
        assertEquals("Cannot divide by zero.", exception.getMessage());
    }
}

