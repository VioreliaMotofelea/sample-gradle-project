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
    void average_basic() {
        double[] input = {1.0, 2.0, 3.0};
        assertEquals(2.0, PrimitiveDoubleStreams.average(input), 1e-9);
    }

    @Test
    void top10Percent_basic() {
        double[] input = {1,2,3,4,5,6,7,8,9,10};
        List<Double> top = PrimitiveDoubleStreams.top10Percent(input);
        assertEquals(1, top.size());
        assertEquals(10.0, top.get(0));
    }
}
