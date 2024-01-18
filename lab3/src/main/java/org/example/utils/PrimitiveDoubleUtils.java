package org.example.utils;

import java.util.Arrays;
import java.util.Comparator;

public class PrimitiveDoubleUtils {
    private static double generateRandomBigDecimal() {
        return Math.random();
    }
    public static double[] generateRandomBigDecimalList(int size) {
        double[] list = new double[size];
        for (int i = 0; i < size; i++) {
            list[i] = generateRandomBigDecimal();
        }
        return list;
    }

    public static double[] generateSortedBigDecimalList(int size, boolean ascending) {
        double[] list = generateRandomBigDecimalList(size);
        if(ascending) {
            return Arrays.stream(list).sorted().toArray();
        } else {
            return Arrays.stream(list).boxed().sorted(Comparator.reverseOrder()).mapToDouble(Double::doubleValue).toArray();
        }
    }

    public static double sum(double[] numbers) {
        return Arrays.stream(numbers)
                .reduce((double) 0, Double::sum);
    }

    public static double sumParallel(double[] numbers) {
        return Arrays.stream(numbers)
                .parallel()
                .reduce((double) 0, Double::sum);
    }

    public static double average(double[] numbers) {
        return Arrays.stream(numbers)
                .reduce((double) 0, Double::sum)
                / numbers.length;
    }

    public static double averageParallel(double[] numbers) {
        return Arrays.stream(numbers)
                .parallel()
                .reduce((double) 0, Double::sum)
                / numbers.length;
    }

    public static double[] getTop10Percent(double[] numbers) {
        return Arrays.stream(numbers)
                .sorted()
                .limit(numbers.length / 10)
                .toArray();
    }
}
