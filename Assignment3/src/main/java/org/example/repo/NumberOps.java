package org.example.repo;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class NumberOps<T> {

    private List<T> list;

    public NumberOps(List<T> list) {
        this.list = list;
    }

    public T sum() {
        return list.stream().reduce(zero(), this::add);
    }

    public T average() {
        return divide(sum(), list.size());
    }

    public List<T> top10perc() {
        int tenPerc = list.size() / 10;
        return list.stream()
                .sorted(comparator())
                .limit(tenPerc)
                .collect(Collectors.toList());
    }


    protected abstract T zero();
    protected abstract T add(T a, T b);

    protected abstract T divide(T a, int b);

    protected abstract Comparator<? super T> comparator();
}
