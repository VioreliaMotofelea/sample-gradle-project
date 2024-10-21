package com.example.calculator.calculator;

import com.example.calculator.operations.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestCalculator {
    private CalculatorInterface calculator;
    private Operation addOperation;
    private Operation subtractOperation;
    private Operation multiplyOperation;
    private Operation divideOperation;
    private Operation sqrtOperation;
    private Operation minOperation;
    private Operation maxOperation;

    @BeforeEach
    public void setup() {
        addOperation = mock(Operation.class);
        subtractOperation = mock(Operation.class);
        multiplyOperation = mock(Operation.class);
        divideOperation = mock(Operation.class);
        sqrtOperation = mock(Operation.class);
        minOperation = mock(Operation.class);
        maxOperation = mock(Operation.class);

        Map<String, Operation> operations = new HashMap<>();
        operations.put("+", addOperation);
        operations.put("-", subtractOperation);
        operations.put("*", multiplyOperation);
        operations.put("/", divideOperation);
        operations.put("sqrt", sqrtOperation);
        operations.put("min", minOperation);
        operations.put("max", maxOperation);

        calculator = new Calculator(operations);
    }

    @Test
    public void testAdd() {
        when(addOperation.calculate(2.0, 2.0)).thenReturn(4.0);
        assertEquals(4.0, calculator.calculate(2.0, 2.0, "+"), 0.00001);
    }

    @Test
    public void testSubtract() {
        when(subtractOperation.calculate(2.0, 2.0)).thenReturn(0.0);
        assertEquals(0.0, calculator.calculate(2.0, 2.0, "-"), 0.00001);
    }

    @Test
    public void testMultiply() {
        when(multiplyOperation.calculate(2.0, 2.0)).thenReturn(4.0);
        assertEquals(4.0, calculator.calculate(2.0, 2.0, "*"), 0.00001);
    }

    @Test
    public void testDivide() {
        when(divideOperation.calculate(2.0, 2.0)).thenReturn(1.0);
        assertEquals(1.0, calculator.calculate(2.0, 2.0, "/"), 0.00001);
    }

    @Test
    public void testSqrt() {
        when(sqrtOperation.calculate(4.0, 0)).thenReturn(2.0);
        assertEquals(2.0, calculator.calculate(4.0, 0.0, "sqrt"), 0.00001);
    }

    @Test
    public void testMin() {
        when(minOperation.calculate(2.0, 4.0)).thenReturn(2.0);
        assertEquals(2.0, calculator.calculate(2.0, 4.0, "min"), 0.00001);
    }

    @Test
    public void testMax() {
        when(maxOperation.calculate(2.0, 4.0)).thenReturn(4.0);
        assertEquals(4.0, calculator.calculate(2.0, 4.0, "max"), 0.00001);
    }

    @Test
    public void testInvalidOperator() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate(2.0, 2.0, "invalid"));
    }
}
