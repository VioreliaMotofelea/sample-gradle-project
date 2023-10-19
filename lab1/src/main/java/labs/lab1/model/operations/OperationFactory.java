package labs.lab1.model.operations;

import labs.lab1.model.operations.impl.*;

public class OperationFactory {
    public static IOperation createOperation(String operation, Double... operators) {
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
            default:
                return null;
        }
    }
}
