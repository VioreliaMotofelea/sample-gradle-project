package com.tora.StreamOperations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BigDecimalOperation {
    public BigDecimal addList(List<BigDecimal> list){
        return list.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal averageOfList(List<BigDecimal> list){
        return list.stream().reduce(BigDecimal.ZERO, BigDecimal::add).divide(BigDecimal.valueOf(list.size()));
    }

    public List<BigDecimal> top10OfList(List<BigDecimal> list){
        int tenPercent = Math.max(1, Math.round((float) list.size() / 10));
        return list.stream().sorted(Comparator.reverseOrder()).limit(tenPercent).collect(Collectors.toList());
    }
}
