package com.example;

import java.util.Arrays;
import java.util.List;

public  class Utils {
    public static boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean isOperator(String s) {
        List<String> VALID_OPERATORS = Arrays.asList(
                "+", "-", "*", "/", "min", "max"
        );
        return VALID_OPERATORS.contains(s);
    }
}
