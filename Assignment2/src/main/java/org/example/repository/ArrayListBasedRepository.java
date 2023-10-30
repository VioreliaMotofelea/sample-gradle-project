package org.example.repository;

import java.util.ArrayList;
import java.util.List;

public class ArrayListBasedRepository<T> implements InMemoryRepository<T> {

    private List<T> list;

    public ArrayListBasedRepository() {
        this.list = new ArrayList<T>();
    }

    @Override
    public void add(T item) {
        list.add(item);
    }

    @Override
    public void remove(T item) {
        list.remove(item);
    }

    @Override
    public boolean contains(T item) {
        return list.contains(item);
    }

    @Override
    public void clear() {
        list.clear();
    }
}
