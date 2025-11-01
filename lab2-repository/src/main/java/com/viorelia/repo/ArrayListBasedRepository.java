package com.viorelia.repo;

import java.util.ArrayList;
import java.util.List;

public class ArrayListBasedRepository<T> implements InMemoryRepository<T> {
    private final List<T> list = new ArrayList<>();
    @Override public void add(T t) { list.add(t); }
    @Override public boolean contains(T t) { return list.contains(t); }
    @Override public void remove(T t) { list.remove(t); }
}
