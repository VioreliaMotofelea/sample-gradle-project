package org.example.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DoubleUtils {
    private static Double generateRandomBigDecimal() {
        return Math.random();
    }
    public static List<Double> generateRandomBigDecimalList(int size) {
        List<Double> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(generateRandomBigDecimal());
        }
        return list;
    }

    public static List<Double> generateSortedBigDecimalList(int size, boolean ascending) {
        List<Double> list = generateRandomBigDecimalList(size);
        if(ascending) {
            list.sort(Comparator.naturalOrder());
        } else {
            list.sort(Comparator.reverseOrder());
        }
        return list;
    }

    public static Double sum(List<Double> numbers) {
        return numbers
                .stream()
                .reduce((double) 0, Double::sum);
    }

    public static Double sumParallel(List<Double> numbers) {
        return numbers
                .parallelStream()
                .reduce((double) 0, Double::sum);
    }

    public static Double average(List<Double> numbers) {
        return numbers.stream()
                .reduce((double) 0, Double::sum)
                / numbers.size();
    }

    public static Double averageParallel(List<Double> numbers) {
        return numbers.parallelStream()
                .reduce((double) 0, Double::sum)
                / numbers.size();
    }

    public static List<Double> getTop10Percent(List<Double> numbers) {
        return numbers.stream()
                .sorted()
                .limit(numbers.size() / 10)
                .collect(Collectors.toList());
    }
}
