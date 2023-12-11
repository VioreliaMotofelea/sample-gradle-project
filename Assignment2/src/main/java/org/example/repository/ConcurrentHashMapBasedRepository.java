package org.example.repository;

import java.util.AbstractMap;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapBasedRepository<T> implements InMemoryRepository<T> {

    private final AbstractMap<Integer, T> map;

    public ConcurrentHashMapBasedRepository() {
        map = new ConcurrentHashMap<>();
    }

    @Override
    public void add(T item) {
        map.put(item.hashCode(), item);
    }

    @Override
    public void remove(T item) {
        map.remove(item.hashCode());
    }

    @Override
    public boolean contains(T item) {
        return map.containsKey(item.hashCode());
    }

    @Override
    public void clear() {
        map.clear();
    }
}
