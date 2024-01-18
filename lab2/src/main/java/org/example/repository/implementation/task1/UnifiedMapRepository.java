package org.example.repository.implementation.task1;

import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.example.repository._interface.IInMemoryRepository;

import java.util.Map;

public class UnifiedMapRepository<Key extends Comparable<Key>> implements IInMemoryRepository<Key> {
    private final Map<Key, Integer> entities;

    public UnifiedMapRepository() {
        this.entities = new UnifiedMap<>();
    }

    @Override
    public void add(Key entity) {
        this.entities.put(entity, 1024);
    }

    @Override
    public void remove(Key entity) {
        this.entities.remove(entity);
    }

    @Override
    public boolean contains(Key entity) {
        return this.entities.containsKey(entity);
    }
}
