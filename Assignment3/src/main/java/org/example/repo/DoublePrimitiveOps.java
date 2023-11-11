package org.example.repo;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.ints.IntArrayList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class DoublePrimitiveOps {

    AbstractDoubleList list;

    public DoublePrimitiveOps(DoubleArrayList list) {
        this.list = list;
    }

    public double sum() {
        return list.stream().reduce(0.0, (a, b) -> a + b);
    }

    public double average() {
        return sum() / list.size();
    }

    public DoubleArrayList top10perc() {
        return list.stream()
                .sorted(Comparator.reverseOrder())
                .limit((int) (list.size() * 0.1))
                .collect(Collectors.toCollection(DoubleArrayList::new));
    }
}
