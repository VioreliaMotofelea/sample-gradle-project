package com.viorelia.lab3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class PrimitiveDoubleStreams {

    private PrimitiveDoubleStreams() {
    }

    public static double sum(double[] values) {
        return Arrays.stream(values).sum();
    }

    public static double average(double[] values) {
        return Arrays.stream(values)
                .average()
                .orElseThrow(() -> new IllegalArgumentException("Cannot compute average of empty array"));
    }

    public static List<Double> top10Percent(double[] values) {
        if (values.length == 0) {
            return List.of();
        }
        int n = Math.max(1, (int) Math.ceil(values.length * 0.10));

        return Arrays.stream(values)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .limit(n)
                .collect(Collectors.toList());
    }
}
