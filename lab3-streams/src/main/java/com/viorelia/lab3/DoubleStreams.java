package com.viorelia.lab3;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class DoubleStreams {

    private DoubleStreams() {
    }

    public static double sum(List<Double> values) {
        return values.stream()
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public static double average(List<Double> values) {
        return values.stream()
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElseThrow(() -> new IllegalArgumentException("Cannot compute average of empty list"));
    }

    public static List<Double> top10Percent(List<Double> values) {
        List<Double> cleaned = values.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (cleaned.isEmpty()) {
            return List.of();
        }
        int n = Math.max(1, (int) Math.ceil(cleaned.size() * 0.10));

        return cleaned.stream()
                .sorted(Comparator.reverseOrder())
                .limit(n)
                .collect(Collectors.toList());
    }
}
