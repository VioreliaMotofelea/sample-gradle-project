package com.viorelia.lab3;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrimitiveDoubleStreamsTest {

    @Test
    void sum_basic() {
        double[] input = {1.5, 2.5, 3.0};
        assertEquals(7.0, PrimitiveDoubleStreams.sum(input), 1e-9);
    }

    @Test
    void sum_withNegativesAndZero() {
        double[] input = {-2.0, 0.0, 5.0};
        assertEquals(3.0, PrimitiveDoubleStreams.sum(input), 1e-9);
    }

    @Test
    void sum_emptyArray_isZero() {
        double[] input = {};
        assertEquals(0.0, PrimitiveDoubleStreams.sum(input), 1e-9);
    }

    @Test
    void average_basic() {
        double[] input = {1.0, 2.0, 3.0};
        assertEquals(2.0, PrimitiveDoubleStreams.average(input), 1e-9);
    }

    @Test
    void average_throwsOnEmptyArray() {
        double[] input = {};
        assertThrows(IllegalArgumentException.class,
                () -> PrimitiveDoubleStreams.average(input));
    }

    @Test
    void average_is_independent_of_order() {
        double[] vals = {1.0, 5.0, 3.0, 7.0, 9.0};
        double[] asc  = {1.0, 3.0, 5.0, 7.0, 9.0};
        double[] desc = {9.0, 7.0, 5.0, 3.0, 1.0};

        double a1 = PrimitiveDoubleStreams.average(vals);
        double a2 = PrimitiveDoubleStreams.average(asc);
        double a3 = PrimitiveDoubleStreams.average(desc);

        assertEquals(a1, a2, 1e-9);
        assertEquals(a1, a3, 1e-9);
    }

    @Test
    void top10Percent_basic() {
        double[] input = {1,2,3,4,5,6,7,8,9,10};
        List<Double> top = PrimitiveDoubleStreams.top10Percent(input);
        assertEquals(1, top.size());
        assertEquals(10.0, top.get(0));
    }

    @Test
    void top10Percent_roundsUpAndSortedDesc() {
        double[] input = {5, 1, 20, 3, 10, 7, 15, 2, 8, 4, 6, 9, 11, 12, 13};
        List<Double> top = PrimitiveDoubleStreams.top10Percent(input);

        assertEquals(2, top.size());
        assertEquals(20.0, top.get(0));
        assertEquals(15.0, top.get(1));
    }
}
