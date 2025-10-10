package com.example;

public class SimpleEvaluator implements Evaluator {
  @Override
  public int evaluate(BinaryExpression expression) {
    return expression.getOperator().apply(expression.getLeftOperand(), expression.getRightOperand());
  }
}
