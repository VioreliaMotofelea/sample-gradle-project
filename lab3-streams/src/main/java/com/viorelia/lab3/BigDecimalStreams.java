package com.viorelia.lab3;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class BigDecimalStreams {

    private static final MathContext AVG_CONTEXT = MathContext.DECIMAL64;

    private BigDecimalStreams() {
    }

    public static BigDecimal sum(List<BigDecimal> values) {
        return values.stream()
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static BigDecimal average(List<BigDecimal> values) {
        long count = values.stream().filter(Objects::nonNull).count();
        if (count == 0) {
            throw new IllegalArgumentException("Cannot compute average of empty list");
        }
        BigDecimal total = sum(values);
        return total.divide(BigDecimal.valueOf(count), AVG_CONTEXT);
    }

    public static List<BigDecimal> top10Percent(List<BigDecimal> values) {
        List<BigDecimal> cleaned = values.stream()
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
