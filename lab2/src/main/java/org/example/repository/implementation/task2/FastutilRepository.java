package org.example.repository.implementation.task2;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import org.example.repository._interface.IInMemoryRepository;

public class  FastutilRepository
        implements IInMemoryRepository<Integer> {
    IntSet entities;

    public FastutilRepository() {
        entities = new IntOpenHashSet();
    }

    @Override
    public void add(Integer entity) {
        entities.add((int) entity);
    }

    @Override
    public void remove(Integer entity) {
        entities.remove((int) entity);
    }

    @Override
    public boolean contains(Integer entity) {
        return entities.contains((int) entity);
    }
}
