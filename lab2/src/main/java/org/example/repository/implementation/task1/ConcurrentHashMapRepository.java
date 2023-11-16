package org.example.repository.implementation.task1;

import org.example.repository._interface.IInMemoryRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapRepository<Key extends Comparable<Key>> implements IInMemoryRepository<Key> {
    private final Map<Key, Integer> entities;

    public ConcurrentHashMapRepository() {
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
