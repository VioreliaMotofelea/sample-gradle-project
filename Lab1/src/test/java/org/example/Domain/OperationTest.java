package org.example.Domain;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OperationTest {

    private final float delta = 0.00001F;

    @Test
    void testOperation(){
        Operand operand1 = new Operand(2);
        Operand operand2 = new Operand(3);

        Operation operation = new Operation();
        operation.setOperator(Operator.add);
        operation.setOperand1(operand1);
        operation.setOperand2(operand2);

        assertEquals(operation.getOperator(),Operator.add);
        assertEquals(operation.getOperand1().getValue(), operand1.getValue(), delta);
        assertEquals(operation.getOperand2().getValue(), operand2.getValue(), delta);
    }

    @Test
    void testNestedOperation(){
        Operand operand1 = new Operand(2);
        Operand operand2 = new Operand();

        Operand subOperand = new Operand(3);
        operand2.setOperand1(subOperand);
        operand2.setOperand2(subOperand);
        operand2.setOperator(Operator.divide);

        Operation operation;
        operation = new Operation();
        operation.setOperator(Operator.add);
        operation.setOperand1(operand1);
        operation.setOperand2(operand2);

        assertNull(operation.getOperand2().getValue());
    }

}