package com.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleParser implements Parser {
  private static final Pattern PATTERN =
          Pattern.compile("\\s*([+-]?\\d+)\\s*([+\\-*/])\\s*([+-]?\\d+)\\s*");

  @Override
  public BinaryExpression parse(String expression) {
    Matcher matcher = PATTERN.matcher(expression);
    if(!matcher.matches()) {
      throw new IllegalArgumentException("Invalid expression!");
    }

    int leftOperand = Integer.parseInt(matcher.group(1));
    char operatorSymbol = matcher.group(2).charAt(0);
    int rightOperand = Integer.parseInt(matcher.group(3));

    return new BinaryExpression(leftOperand, Operator.fromSymbol(operatorSymbol), rightOperand);
  }
}
