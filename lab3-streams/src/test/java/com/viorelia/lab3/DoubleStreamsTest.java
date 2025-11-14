package com.viorelia.lab3;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DoubleStreamsTest {

    @Test
    void sum_basic() {
        List<Double> input = List.of(1.5, 2.5, 3.0);
        assertEquals(7.0, DoubleStreams.sum(input), 1e-9);
    }

    @Test
    void average_basic() {
        List<Double> input = List.of(1.0, 2.0, 3.0);
        assertEquals(2.0, DoubleStreams.average(input), 1e-9);
    }

    @Test
    void top10Percent_basic() {
        List<Double> input = List.of(1d,2d,3d,4d,5d,6d,7d,8d,9d,10d);
        List<Double> top = DoubleStreams.top10Percent(input);
        assertEquals(1, top.size());
        assertEquals(10.0, top.get(0));
    }
}
