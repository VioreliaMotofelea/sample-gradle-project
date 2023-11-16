package org.example.repository.implementation.task1;

import org.eclipse.collections.impl.map.mutable.ConcurrentHashMap;
import org.example.repository._interface.IInMemoryRepository;

import java.util.Map;

public class ConcurrentHashMapEclipseRepository<Key extends Comparable<Key>> implements IInMemoryRepository<Key> {
    private final Map<Key, Integer> entities;

    public ConcurrentHashMapEclipseRepository() {
        this.entities = new ConcurrentHashMap<>();
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
