package org.example.repository.implementation.task0;

import org.example.entity.Order;
import org.example.repository._interface.IInMemoryRepository;

import java.util.Set;
import java.util.TreeSet;

public class TreeSetRepository<T extends Comparable<T>>
        implements IInMemoryRepository<T> {
    private final Set<T> entities;

    public TreeSetRepository() {
        this.entities = new TreeSet<>();
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
