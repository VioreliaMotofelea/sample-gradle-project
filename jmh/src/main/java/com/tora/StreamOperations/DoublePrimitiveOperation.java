package com.tora.StreamOperations;


import java.util.Arrays;
import java.util.Comparator;

public class DoublePrimitiveOperation {
    public double addList(double[] list){
        return Arrays.stream(list).reduce(0, Double::sum);
    }

    public double averageOfList(double[] list){
        return Arrays.stream(list).average().orElse(0);
    }

    public double[] top10NotOfList(double[] list){
        int tenPercent = Math.max(1, Math.round((float) list.length / 10));
        return Arrays.stream(list).sorted().limit(tenPercent).toArray();
    }
}
