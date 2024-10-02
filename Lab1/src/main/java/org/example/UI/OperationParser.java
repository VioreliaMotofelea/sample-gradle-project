package org.example.UI;

import org.example.Domain.Operation;
import org.example.Domain.Operator;

import java.util.List;
import java.util.Locale;

public class OperationParser {
    //sqrt 4 + 2 * min 2.5 3

    public static Operation parseLine(String line) {
        line = line.toLowerCase(Locale.ROOT);
        List<String> terms = List.of(line.split(" "));

        int index = 0;
        Operation currentOperation = new Operation();

        while(index < terms.size()){
            if(isNumber(terms.get(index))){
                float nr1 = convertToFloat(terms.get(index));
                index++;
                Operator operator = convertToOperator(terms.get(index));
                index++;
                float nr2 = convertToFloat(terms.get(index));
                //


            }
        }
    }

    private static boolean isNumber(String string){
        try{
            Float.parseFloat(string);
        }catch (NumberFormatException ex){
            return false;
        }
        return true;
    }

    private static float convertToFloat(String string){
        return Float.parseFloat(string);
    }

    private static Operator convertToOperator(String op){
        switch (op){
            case "+":
                return Operator.add;
            case "-":
                return Operator.subtract;
            case "/":
                return Operator.divide;
            case "*":
                return Operator.multiply;
            case "max":
                return Operator.max;
            case "min":
                return Operator.min;
            case "sqrt":
                return Operator.sqrt;
            default:
                return Operator.add;
            }
    }
}
