package org.example.repository.implementation.task0;

import org.example.repository._interface.IInMemoryRepository;

import java.util.ArrayList;
import java.util.List;

public class ArrayListRepository<T extends Comparable<T>>
        implements IInMemoryRepository<T> {
    private final List<T> entities;

    public ArrayListRepository() {
        this.entities = new ArrayList<>();
    }

    @Override
    public void add(T entity) {
        entities.add(entity);
    }

    @Override
    public void remove(T entity) {
        entities.remove(entity);
    }

    @Override
    public boolean contains(T entity) {
        return entities.contains(entity);
    }
}
