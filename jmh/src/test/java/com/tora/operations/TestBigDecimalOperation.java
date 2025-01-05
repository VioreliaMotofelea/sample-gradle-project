package com.tora.operations;

import com.tora.StreamOperations.BigDecimalOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestBigDecimalOperation {
    private BigDecimalOperation bigDOp;

    private List<BigDecimal> list;

    @BeforeEach
    public void setup() {
        bigDOp = new BigDecimalOperation();
        list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(BigDecimal.valueOf(i));
        }
    }

    @Test
    public void testAddList() {
        BigDecimal sum = BigDecimal.valueOf(4950);
        BigDecimal result = bigDOp.addList(list);
        Assertions.assertEquals(sum, result);
    }

    @Test
    public void testAverageOfList() {
        BigDecimal average = BigDecimal.valueOf(49.5);
        BigDecimal result = bigDOp.averageOfList(list);
        Assertions.assertEquals(average, result);
    }

    @Test
    public void testTop10OfList() {
        List<BigDecimal> top10 = new ArrayList<>();
        for (int i = 99; i > 89; i--) {
            top10.add(BigDecimal.valueOf(i));
        }
        List<BigDecimal> result = bigDOp.top10OfList(list);
        Assertions.assertEquals(top10, result);
    }

}
