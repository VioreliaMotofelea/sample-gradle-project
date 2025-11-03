package com.viorelia.repo;

import java.util.Set;
import java.util.TreeSet;

public class TreeSetBasedRepository<T extends Comparable<? super T>>
        implements InMemoryRepository<T> {
    private final Set<T> set = new TreeSet<>();

    @Override public void add(T t) { set.add(t); }
    @Override public boolean contains(T t) { return set.contains(t); }
    @Override public void remove(T t) { set.remove(t); }
}
