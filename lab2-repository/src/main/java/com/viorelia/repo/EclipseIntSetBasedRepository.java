package com.viorelia.repo;

import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;

public class EclipseIntSetBasedRepository implements InMemoryRepository<Order> {
    private final IntHashSet set = new IntHashSet();

    @Override public void add(Order o) { set.add(o.id()); }
    @Override public boolean contains(Order o) { return set.contains(o.id()); }
    @Override public void remove(Order o) { set.remove(o.id()); }
}
