package com.tora.operations;

import com.tora.StreamOperations.DoublePrimitiveOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class TestDoublePrimitiveOperation {
    public DoublePrimitiveOperation doubleOp;

    public double[] list;

    @BeforeEach
    public void setup() {
        doubleOp = new DoublePrimitiveOperation();
        list = new double[100];
        for (int i = 0; i < 100; i++) {
            list[i] = i;
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
    public void testTop10NotOfList() {
        double[] top10 = new double[10];
        for (int i = 0; i < 10; i++) {
            top10[i] = i;
        }
        double[] result = doubleOp.top10NotOfList(list);
        Assertions.assertArrayEquals(top10, result);
    }
}
