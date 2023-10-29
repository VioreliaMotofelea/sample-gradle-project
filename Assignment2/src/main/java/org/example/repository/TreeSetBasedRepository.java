package org.example.repository;

import java.util.Set;
import java.util.TreeSet;

public class TreeSetBasedRepository<T extends Comparable<T>> implements InMemoryRepository<T> {

    private final Set<T> set;

    public TreeSetBasedRepository() {
        this.set = new TreeSet<T>();
    }

    @Override
    public void add(T item) {
        set.add(item);
    }

    @Override
    public void remove(T item) {
        set.remove(item);
    }

    @Override
    public boolean contains(T item) {
        return set.contains(item);
    }
}
