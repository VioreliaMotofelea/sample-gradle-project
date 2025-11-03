package com.viorelia.repo;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

public class FastutilSetBasedRepository<T> implements InMemoryRepository<T> {
    private final ObjectOpenHashSet<T> set = new ObjectOpenHashSet<>();

    @Override public void add(T t) { set.add(t); }
    @Override public boolean contains(T t) { return set.contains(t); }
    @Override public void remove(T t) { set.remove(t); }
}
