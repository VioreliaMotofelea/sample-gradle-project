package org.example.repository;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public class FastutilArraylistBasedRepo<T> implements InMemoryRepository<T> {

    private ObjectArrayList<T> arrayList;

    public FastutilArraylistBasedRepo() {
        arrayList = new ObjectArrayList<>();
    }

    @Override
    public void add(T item) {
        arrayList.add(item);
    }

    @Override
    public void remove(T item) {
        arrayList.remove(item);
    }

    @Override
    public boolean contains(T item) {
        return arrayList.contains(item);
    }

    @Override
    public void clear() {
        arrayList.clear();
    }
}
