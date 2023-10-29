package org.example.repository;

import java.util.HashSet;

public class HashSetBasedRepository<T> implements InMemoryRepository<T> {

    private HashSet<T> set;

    public HashSetBasedRepository() {
        this.set = new HashSet<T>();
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
