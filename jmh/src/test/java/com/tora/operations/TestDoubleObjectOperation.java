package com.tora.operations;

import com.tora.StreamOperations.DoubleObjectOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class TestDoubleObjectOperation {
    public DoubleObjectOperation doubleOp;

    public List<Double> list;

    @BeforeEach
    public void setup() {
        doubleOp = new DoubleObjectOperation();
        list = new ArrayList<>();
        for (double i = 0; i < 100; i++) {
            list.add(i);
        }
    }

    @Test
    public void testAddList() {
        double sum = 4950;
        double result = doubleOp.addList(list);
        Assertions.assertEquals(sum, result);
    }

    @Test
    public void testAverageOfList() {
        double average = 49.5;
        double result = doubleOp.averageOfList(list);
        Assertions.assertEquals(average, result);
    }

    @Test
    public void testTop10OfList() {
        List<Double> top10 = new ArrayList<>();
        for (double i = 99; i > 89; i--) {
            top10.add(i);
        }
        List<Double> result = doubleOp.top10OfList(list);
        Assertions.assertEquals(top10, result);
    }
}
