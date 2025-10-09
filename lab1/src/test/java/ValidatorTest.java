package com.example.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = new Validator();
    }

    @Test
    void testValidExpressionSimple() {
        String[] tokens = {"3", "+", "5"};
        assertDoesNotThrow(() -> validator.validate(tokens));
    }

    @Test
    void testValidExpressionWithMinMax() {
        String[] tokens = {"min", "3", "4", "+", "5"};
        assertDoesNotThrow(() -> validator.validate(tokens));

        String[] tokens2 = {"max", "1", "2", "*", "min", "3", "4"};
        assertDoesNotThrow(() -> validator.validate(tokens2));
    }

    @Test
    void testNullOrShortExpression() {
        String[] tokens2 = {"3"};
        String[] tokens3 = {"+"};

        assertThrows(ValidationException.class, () -> validator.validate(tokens2));
        assertThrows(ValidationException.class, () -> validator.validate(tokens3));
    }

    @Test
    void testInvalidOperand() {
        String[] tokens = {"3", "+", "x"};
        ValidationException ex = assertThrows(ValidationException.class, () -> validator.validate(tokens));
        assertTrue(ex.getErrors().get(0).contains("Expected a number"));
    }

    @Test
    void testInvalidOperator() {
        String[] tokens = {"3", "5", "3"};
        ValidationException ex = assertThrows(ValidationException.class, () -> validator.validate(tokens));
        assertTrue(ex.getErrors().get(0).contains("Expected an operator"));
    }

    @Test
    void testExpressionEndsWithOperator() {
        String[] tokens = {"2", "*", "3", "+"};
        ValidationException ex = assertThrows(ValidationException.class, () -> validator.validate(tokens));
        assertTrue(ex.getErrors().get(0).contains("Expression cannot end with an operator"));
    }


}
