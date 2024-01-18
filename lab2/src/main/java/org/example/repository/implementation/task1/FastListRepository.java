package org.example.repository.implementation.task1;

import org.eclipse.collections.impl.list.mutable.FastList;
import org.example.repository._interface.IInMemoryRepository;

import java.util.List;

public class FastListRepository<T extends Comparable<T>> implements IInMemoryRepository<T> {
    private final List<T> entities;

    public FastListRepository() {
        this.entities = new FastList<>();
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
