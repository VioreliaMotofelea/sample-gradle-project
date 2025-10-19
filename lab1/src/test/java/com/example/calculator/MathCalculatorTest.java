package com.example.calculator;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MathCalculatorTest {

    private MathCalculator mathCalculator;

    @BeforeEach
    void setUp() {
        mathCalculator = new MathCalculator();
    }

    @Test
    void testMin() {
        assertEquals(3.0, mathCalculator.min(3.0, 5.0));
        assertEquals(-10.0, mathCalculator.min(-5.0, -10.0));
        assertEquals(7.0, mathCalculator.min(7.0, 7.0));
    }

    @Test
    void testMax() {
        assertEquals(5.0, mathCalculator.max(3.0, 5.0));
        assertEquals(-5.0, mathCalculator.max(-5.0, -10.0));
        assertEquals(7.0, mathCalculator.max(7.0, 7.0));
    }

    @Test
    void testSqrt() {
        assertEquals(3.0, mathCalculator.sqrt(9.0));
        assertEquals(5.0, mathCalculator.sqrt(25.0));
        assertEquals(0.0, mathCalculator.sqrt(0.0));
    }

    @Test
    void testSqrtOfNegativeNumberThrowsException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> mathCalculator.sqrt(-9.0)
        );
        assertEquals("Cannot calculate the square root of a negative number.", exception.getMessage());
    }
}

