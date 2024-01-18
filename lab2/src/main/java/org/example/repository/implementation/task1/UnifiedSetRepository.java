package org.example.repository.implementation.task1;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.example.repository._interface.IInMemoryRepository;

import java.util.Set;

public class UnifiedSetRepository<T extends Comparable<T>> implements IInMemoryRepository<T> {
    private final Set<T> entities;

    public UnifiedSetRepository() {
        this.entities = new UnifiedSet<>();
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
