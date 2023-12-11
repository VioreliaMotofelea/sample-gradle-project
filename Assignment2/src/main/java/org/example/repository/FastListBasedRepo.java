package org.example.repository;

import org.eclipse.collections.impl.list.mutable.FastList;

public class FastListBasedRepo<T> implements InMemoryRepository<T>{

    private FastList<T> fastList;

    public FastListBasedRepo() {
        fastList = new FastList<>();
    }

    @Override
    public void add(T item) {
        fastList.add(item);
    }

    @Override
    public void remove(T item) {
        fastList.remove(item);
    }

    @Override
    public boolean contains(T item) {
        return fastList.contains(item);
    }

    @Override
    public void clear() {
        fastList.clear();
    }
}
