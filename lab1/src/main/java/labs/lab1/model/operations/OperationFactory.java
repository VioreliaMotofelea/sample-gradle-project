package labs.lab1.model.operations;

import labs.lab1.model.operations.impl.*;

public class OperationFactory {
    public static IOperation createOperation(String operation, double... operators) {
        switch (operation) {
            case "+":
                return new Addition(operators);
            case "-":
                return new Subtraction(operators);
            case "*":
                return new Multiplication(operators);
            case "/":
                return new Division(operators);
            case "%":
                return new Modulo(operators);
            case "sqrt":
                return new SquareRoot(operators);
            case "min":
                return new Minimum(operators);
            case "max":
                return new Maximum(operators);
            default:
                throw new UnsupportedOperationException("Operation not supported!");
        }
    }
}
