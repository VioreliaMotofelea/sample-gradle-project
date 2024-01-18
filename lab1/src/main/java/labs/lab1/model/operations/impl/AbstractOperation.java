package labs.lab1.model.operations.impl;

import labs.lab1.model.operations.IOperation;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class AbstractOperation implements IOperation {
    protected final ArrayList<Double> operators;

    public AbstractOperation(int operatorsMinimum, double... operators) {
        if(operators.length < operatorsMinimum) {
            throw new InvalidParameterException("To few parameters were provided!");
        }

        this.operators = new ArrayList<>();
        Arrays.stream(operators).forEach(this.operators::add);
    }

    public AbstractOperation(int operatorsMinimum, int operatorsMaximum, double... operators) {
        if(operators.length < operatorsMinimum) {
            throw new InvalidParameterException("To few parameters were provided!");
        }
        if(operators.length > operatorsMaximum) {
            throw new InvalidParameterException("To many parameters were provided!");
        }

        this.operators = new ArrayList<>();
        Arrays.stream(operators).forEach(this.operators::add);
    }
}
