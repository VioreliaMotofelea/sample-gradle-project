package com.tora.StreamOperations;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DoubleObjectOperation {
    public Double addList(List<Double> list){
        return list.stream().reduce(0.0, Double::sum);
    }

    public Double averageOfList(List<Double> list){
        return list.stream().reduce(0.0, Double::sum) / list.size();
    }

    public List<Double> top10OfList(List<Double> list){
        int tenPercent = Math.max(1, Math.round((float) list.size() / 10));
        return list.stream().sorted(Comparator.reverseOrder()).limit(tenPercent).collect(Collectors.toList());
    }
}
