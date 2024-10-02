package org.example.Domain;

public class Operation {
    protected Operand operand1, operand2;
    protected Operator operator;

    public Operation(Operand operand1, Operand operand2, Operator operator) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
    }
    public Operation() {
    }

    public Operand getOperand1() {
        return operand1;
    }

    public void setOperand1(Operand operand1) {
        this.operand1 = operand1;
    }

    public Operand getOperand2() {
        return operand2;
    }

    public void setOperand2(Operand operand2) {
        this.operand2 = operand2;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
