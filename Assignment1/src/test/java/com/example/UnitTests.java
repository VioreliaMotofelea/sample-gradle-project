package com.example;

import org.example.controller.CalculatorController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UnitTests {

    private CalculatorController calculatorController;

    @BeforeEach
    public void setup() {
        calculatorController = new CalculatorController();
    }

    @Test
    public void shouldPerformAddOperation() {
        assertNull(calculatorController.getPreviousResult());
        calculatorController.performAddOperation(1f, 2f);
        assertEquals(calculatorController.getPreviousResult(), 3f);
    }

    @Test
    public void shouldPerformSubtractOperation() {
        assertNull(calculatorController.getPreviousResult());
        calculatorController.performDeleteOperation(1f, 2f);
        assertEquals(calculatorController.getPreviousResult(), -1f);
    }

    @Test
    public void shouldPerformMultiplyOperation() {
        assertNull(calculatorController.getPreviousResult());
        calculatorController.performMultiplyOperation(3f, 2f);
        assertEquals(calculatorController.getPreviousResult(), 6f);
    }

    @Test
    public void shouldPerformDivideOperation() {
        assertNull(calculatorController.getPreviousResult());
        calculatorController.performDivideOperation(3f, 2f);
        assertEquals(calculatorController.getPreviousResult(), 1.5f);
        assertThrows(ArithmeticException.class, () -> {
            calculatorController.performDivideOperation(3f, 0f);
        });
    }

    @Test
    public void shouldPerformMinOperation() {
        assertNull(calculatorController.getPreviousResult());
        calculatorController.performMinOperation(3f, 2f);
        assertEquals(calculatorController.getPreviousResult(), 2f);
    }

    @Test
    public void shouldPerformMaxOperation() {
        assertNull(calculatorController.getPreviousResult());
        calculatorController.performMaxOperation(3f, 2f);
        assertEquals(calculatorController.getPreviousResult(), 3f);
    }

    @Test
    public void shouldPerformSqrtOperation() {
        assertNull(calculatorController.getPreviousResult());
        calculatorController.performSqrtOperation(4f);
        assertEquals(calculatorController.getPreviousResult(), 2f);
        assertThrows(ArithmeticException.class, () -> {
            calculatorController.performSqrtOperation(-1f);
        });
    }
}
