package com.example;

public class BinaryExpression {
  private final int leftOperand;
  private final Operator operator;
  private final int rightOperand;

  public BinaryExpression(int leftOperand, Operator operator, int rightOperand) {
    this.leftOperand = leftOperand;
    this.rightOperand = rightOperand;
    this.operator = operator;
  }

  public int getLeftOperand() {
    return leftOperand;
  }

  public Operator getOperator() {
    return operator;
  }

  public int getRightOperand() {
    return rightOperand;
  }
}
