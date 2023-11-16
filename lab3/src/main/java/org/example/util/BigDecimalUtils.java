package org.example.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BigDecimalUtils {
    private static BigDecimal generateRandomBigDecimal() {
        return BigDecimal.valueOf(Math.random() * 1024);
    }
    public static List<BigDecimal> generateRandomBigDecimalList(int size) {
        List<BigDecimal> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(generateRandomBigDecimal());
        }
        return list;
    }

    public static BigDecimal sum(List<BigDecimal> list) {
        return list.stream()
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
