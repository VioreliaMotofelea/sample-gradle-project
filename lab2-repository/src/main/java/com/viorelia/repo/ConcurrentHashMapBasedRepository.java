package com.viorelia.repo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentHashMapBasedRepository<T> implements InMemoryRepository<T> {
    private final ConcurrentMap<T, Boolean> map = new ConcurrentHashMap<>();
    @Override public void add(T t) { map.put(t, Boolean.TRUE); }
    @Override public boolean contains(T t) { return map.containsKey(t); }
    @Override public void remove(T t) { map.remove(t); }
}
