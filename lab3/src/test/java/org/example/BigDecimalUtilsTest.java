package org.example;

import org.example.utils.BigDecimalUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BigDecimalUtilsTest {
    @Test
    public void testSumEmptyList() {
        assertEquals(BigDecimal.ZERO, BigDecimalUtils.sum(Collections.emptyList()));
    }

    @Test
    public void testSumVariousValues() {
        List<BigDecimal> numbers = Arrays.asList(BigDecimal.valueOf(10), BigDecimal.valueOf(20), BigDecimal.valueOf(30));
        assertEquals(BigDecimal.valueOf(60), BigDecimalUtils.sum(numbers));
    }

    @Test
    public void testSumNegativeAndPositiveValues() {
        List<BigDecimal> numbers = Arrays.asList(BigDecimal.valueOf(-10), BigDecimal.valueOf(20), BigDecimal.valueOf(-30));
        assertEquals(BigDecimal.valueOf(-20), BigDecimalUtils.sum(numbers));
    }

    @Test
    public void testAverageEmptyList() {
        assertEquals(BigDecimal.ZERO, BigDecimalUtils.average(Collections.emptyList()));
    }

    @Test
    public void testAverageVariousValues() {
        List<BigDecimal> numbers = Arrays.asList(BigDecimal.valueOf(10), BigDecimal.valueOf(20), BigDecimal.valueOf(30));
        assertEquals(BigDecimal.valueOf(20), BigDecimalUtils.average(numbers));
    }

    @Test
    public void testAverageNegativeAndPositiveValues() {
        List<BigDecimal> numbers = Arrays.asList(BigDecimal.valueOf(-10), BigDecimal.valueOf(20), BigDecimal.valueOf(-30));
        assertEquals(BigDecimal.valueOf(-6), BigDecimalUtils.average(numbers));
    }

    @Test
    public void testGetTop10PercentEmptyList() {
        assertTrue(BigDecimalUtils.getTop10Percent(Collections.emptyList()).isEmpty());
    }

    @Test
    public void testGetTop10PercentNonMultipleOf10() {
        List<BigDecimal> numbers = Arrays.asList(BigDecimal.valueOf(1), BigDecimal.valueOf(2), BigDecimal.valueOf(3),
                BigDecimal.valueOf(4), BigDecimal.valueOf(5), BigDecimal.valueOf(6), BigDecimal.valueOf(7),
                BigDecimal.valueOf(8), BigDecimal.valueOf(9), BigDecimal.valueOf(10), BigDecimal.valueOf(11));
        assertEquals(1, BigDecimalUtils.getTop10Percent(numbers).size());
    }

    @Test
    public void testGetTop10PercentCorrectElements() {
        List<BigDecimal> numbers = Arrays.asList(BigDecimal.valueOf(10), BigDecimal.valueOf(20), BigDecimal.valueOf(30),
                BigDecimal.valueOf(40), BigDecimal.valueOf(50), BigDecimal.valueOf(60), BigDecimal.valueOf(70),
                BigDecimal.valueOf(80), BigDecimal.valueOf(90), BigDecimal.valueOf(100));
        List<BigDecimal> top10Percent = BigDecimalUtils.getTop10Percent(numbers);
        assertEquals(1, top10Percent.size());
        assertTrue(top10Percent.contains(BigDecimal.valueOf(100)));
    }

    @Test
    public void testGetTop10PercentEqualValues() {
        List<BigDecimal> numbers = Collections.nCopies(10, BigDecimal.valueOf(50));
        List<BigDecimal> top10Percent = BigDecimalUtils.getTop10Percent(numbers);
        assertEquals(1, top10Percent.size());
        assertTrue(top10Percent.contains(BigDecimal.valueOf(50)));
    }
}
