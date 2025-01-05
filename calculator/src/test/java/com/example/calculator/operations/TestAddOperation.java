package com.example.calculator.operations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;



public class TestAddOperation {

    private Operation addOperation;

    @BeforeEach
    public void setup() {
        addOperation = new AddOperation();
    }

    @Test
    public void testAddPositiveNumbers() {
        Assertions.assertEquals(4.0, addOperation.calculate(2.0, 2.0), 0.00001);
    }

    @Test
    public void testAddNegativeNumbers() {
        Assertions.assertEquals(-4.0, addOperation.calculate(-2.0, -2.0), 0.00001);
    }

    @Test
    public void testAddPositiveAndNegativeNumbers() {
        Assertions.assertEquals(0.0, addOperation.calculate(2.0, -2.0), 0.00001);
    }

    @Test
    public void testAddZero() {
        Assertions.assertEquals(2.0, addOperation.calculate(0.0, 2.0), 0.00001);
    }

    @Test
    public void testAddToString() {
        Assertions.assertEquals("Addition", addOperation.toString());
    }
}
