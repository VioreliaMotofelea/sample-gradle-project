package com.viorelia.repo;

import java.util.HashSet;
import java.util.Set;

public class HashSetBasedRepository<T> implements InMemoryRepository<T> {
    private final Set<T> set = new HashSet<>();
    @Override public void add(T t) { set.add(t); }
    @Override public boolean contains(T t) { return set.contains(t); }
    @Override public void remove(T t) { set.remove(t); }
}
