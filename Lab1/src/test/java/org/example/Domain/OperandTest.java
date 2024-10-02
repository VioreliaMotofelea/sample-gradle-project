package org.example.Domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperandTest {

    private final float delta = 0.00001F;

    @Test
    void getValue() {
        Operand operand = new Operand((float) 2.3);
        assertEquals(2.3, operand.getValue(), delta);
    }

    @Test
    void setValue() {
        Operand operand = new Operand();
        operand.setValue((float)3.4);
        assertEquals(3.4,operand.getValue(), delta);
    }
}