package org.example.repository.implementation.task2;

import com.koloboke.collect.set.IntSet;
import com.koloboke.collect.set.hash.HashIntSets;
import org.example.repository._interface.IInMemoryRepository;

public class KolobokeRepository
        implements IInMemoryRepository<Integer> {

    private final IntSet entities;

    public KolobokeRepository() {
        entities = HashIntSets.newMutableSet();
    }

    @Override
    public void add(Integer entity) {
        entities.add((int) entity);
    }

    @Override
    public void remove(Integer entity) {
        entities.removeInt(entity);
    }

    @Override
    public boolean contains(Integer entity) {
        return entities.contains((int) entity);
    }
}
