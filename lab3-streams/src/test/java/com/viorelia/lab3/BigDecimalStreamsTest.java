package com.viorelia.lab3;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BigDecimalStreamsTest {

    @Test
    void sum_basic() {
        List<BigDecimal> input = List.of(
                new BigDecimal("1.5"),
                new BigDecimal("2.5"),
                new BigDecimal("3.0")
        );
        assertEquals(new BigDecimal("7.0"), BigDecimalStreams.sum(input));
    }

    @Test
    void average_basic() {
        List<BigDecimal> input = List.of(
                new BigDecimal("1.0"),
                new BigDecimal("2.0"),
                new BigDecimal("3.0")
        );
        BigDecimal avg = BigDecimalStreams.average(input);
        assertEquals(new BigDecimal("2"), avg.stripTrailingZeros());
    }

    @Test
    void top10Percent_returns_10_percent_desc() {
        List<BigDecimal> input = List.of(
                new BigDecimal("1"),
                new BigDecimal("2"),
                new BigDecimal("3"),
                new BigDecimal("4"),
                new BigDecimal("5"),
                new BigDecimal("6"),
                new BigDecimal("7"),
                new BigDecimal("8"),
                new BigDecimal("9"),
                new BigDecimal("10")
        );

        List<BigDecimal> top = BigDecimalStreams.top10Percent(input);

        assertEquals(1, top.size());
        assertEquals(new BigDecimal("10"), top.get(0));
    }

    @Test
    void top10Percent_rounds_up_and_handles_empty() {
        assertTrue(BigDecimalStreams.top10Percent(List.of()).isEmpty());

        List<BigDecimal> three = List.of(
                new BigDecimal("1"),
                new BigDecimal("2"),
                new BigDecimal("3")
        );

        assertEquals(1, BigDecimalStreams.top10Percent(three).size());
    }
}
