package org.example.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<BigDecimal> generateSortedBigDecimalList(int size, boolean ascending) {
        List<BigDecimal> list = generateRandomBigDecimalList(size);
        if(ascending) {
            list.sort(Comparator.naturalOrder());
        } else {
            list.sort(Comparator.reverseOrder());
        }
        return list;
    }

    public static BigDecimal sum(List<BigDecimal> numbers) {
        return numbers
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal sumParallel(List<BigDecimal> numbers) {
        return numbers
                .parallelStream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal average(List<BigDecimal> numbers) {
        if (numbers.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return numbers
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(numbers.size()), RoundingMode.CEILING);
    }

    public static BigDecimal averageParallel(List<BigDecimal> numbers) {
        return numbers
                .parallelStream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(numbers.size()), RoundingMode.CEILING);
    }

    public static List<BigDecimal> getTop10Percent(List<BigDecimal> numbers) {
        return numbers.stream()
                .sorted(Comparator.reverseOrder())
                .limit(numbers.size() / 10)
                .collect(Collectors.toList());
    }
}
