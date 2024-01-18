package org.example.repository.implementation.task0;

import org.example.entity.Order;
import org.example.repository._interface.IInMemoryRepository;

import java.util.HashSet;
import java.util.Set;

public class HashSetRepository<T extends Comparable<T>>
        implements IInMemoryRepository<T> {
    private final Set<T> entities;

    public HashSetRepository() {
        this.entities = new HashSet<>();
    }

    @Override
    public void add(T entity) {
        this.entities.add(entity);
    }

    @Override
    public void remove(T entity) {
        this.entities.remove(entity);
    }

    @Override
    public boolean contains(T entity) {
        return this.entities.contains(entity);
    }
}
