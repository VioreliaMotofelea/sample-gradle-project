package org.example.repository.implementation.task2;

import gnu.trove.set.hash.TIntHashSet;
import org.example.repository._interface.IInMemoryRepository;

public class Trove4jRepository
        implements IInMemoryRepository<Integer> {

    private final TIntHashSet entities;

    public Trove4jRepository() {
        entities = new TIntHashSet();
    }

    @Override
    public void add(Integer entity) {
        entities.add(entity);
    }

    @Override
    public void remove(Integer entity) {
        entities.remove(entity);
    }

    @Override
    public boolean contains(Integer entity) {
        return entities.contains(entity);
    }
}
