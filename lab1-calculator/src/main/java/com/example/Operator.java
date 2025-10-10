package com.example;

public enum Operator implements Operation {
  ADD {
    @Override
    public int apply(int a, int b) {
      return a + b;
    }
  },

  SUB {
    @Override
    public int apply(int a, int b) {
      return a - b;
    }
  },

  MUL {
    @Override
    public int apply(int a, int b) {
      return a * b;
    }
  },

  DIV {
    @Override
    public int apply(int a, int b) {
      if(b == 0) {
        throw new ArithmeticException("Cannot divide by zero");
      }

      return a / b;
    }
  };

  public static Operator fromSymbol(char operand) {
    if(operand == '+') return ADD;
    if(operand == '-') return SUB;
    if(operand == '*') return MUL;
    if(operand == '/') return DIV;

    throw new IllegalArgumentException("Invalid operand: " + operand);
  }
}
