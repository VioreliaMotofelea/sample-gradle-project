package com.viorelia.repo;

import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;

public class EclipseSetBasedRepository<T> implements InMemoryRepository<T> {
    private final MutableSet<T> set = new UnifiedSet<>();
    @Override public void add(T t) { set.add(t); }
    @Override public boolean contains(T t) { return set.contains(t); }
    @Override public void remove(T t) { set.remove(t); }
}
