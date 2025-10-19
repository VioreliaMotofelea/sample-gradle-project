package com.example.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    private InputValidator validator;

    @BeforeEach
    void setUp() {
        validator = new InputValidator();
    }

    @Test
    void testValidBinaryInput() {
        assertDoesNotThrow(() -> validator.validate("5 +  3"));
        assertDoesNotThrow(() -> validator.validate("10.5 * 2"));
        assertDoesNotThrow(() -> validator.validate("-5 min 88"));
        assertDoesNotThrow(() -> validator.validate("sqrt 9"));
        assertDoesNotThrow(() -> validator.validate("SQRT 16.5"));
    }


    @Test
    void testEmptyInputThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> validator.validate(""));
        assertThrows(IllegalArgumentException.class, () -> validator.validate("   "));
    }

    @Test
    void testNullInputThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> validator.validate(null));
    }

    @Test
    void testInvalidNumberOfPartsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> validator.validate("5 +"));
        assertThrows(IllegalArgumentException.class, () -> validator.validate("5 + 3 4"));
        assertThrows(IllegalArgumentException.class, () -> validator.validate("sqrt"));
    }

    @Test
    void testInvalidNumberThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> validator.validate("a + 5"));
        assertThrows(IllegalArgumentException.class, () -> validator.validate("5 + b"));
        assertThrows(IllegalArgumentException.class, () -> validator.validate("sqrt c"));
    }

    @Test
    void testUnknownOperatorThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> validator.validate("5 mod 3"));
        assertThrows(IllegalArgumentException.class, () -> validator.validate("5 ^ 2"));
    }

    @Test
    void testInvalidFormatThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> validator.validate("+ 5"));
        assertThrows(IllegalArgumentException.class, () -> validator.validate("max 10"));
    }
}

