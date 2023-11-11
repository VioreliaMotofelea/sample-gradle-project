package com.example;

import org.example.repo.BigDecimalsOps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTests {

    private static final int SIZE = 100;

    private BigDecimalsOps bigDecimalsOps;

    @BeforeEach
    public void setUp() {
        List<BigDecimal> bigDecimalList = new ArrayList<BigDecimal>();

        for (int i = 1; i <= SIZE; i++) {
            bigDecimalList.add(new BigDecimal(BigInteger.valueOf(i)));
        }

        bigDecimalsOps = new BigDecimalsOps(bigDecimalList);
    }

    @Test
    public void testSum() {
        assertEquals(new BigDecimal(5050), bigDecimalsOps.sum());
    }

    @Test
    public void testAverage() {
        assertEquals(new BigDecimal("50.5"), bigDecimalsOps.average());
    }

    @Test
    public void testTop10perc() {
        List<BigDecimal> top10perc = Stream.of(
                new BigDecimal(91),
                new BigDecimal(92),
                new BigDecimal(93),
                new BigDecimal(94),
                new BigDecimal(95),
                new BigDecimal(96),
                new BigDecimal(97),
                new BigDecimal(98),
                new BigDecimal(99),
                new BigDecimal(100)
        )
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        assertEquals(top10perc, bigDecimalsOps.top10perc());
    }
}
