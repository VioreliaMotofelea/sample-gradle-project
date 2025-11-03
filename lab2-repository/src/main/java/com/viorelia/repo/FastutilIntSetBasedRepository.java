package com.viorelia.repo;

import it.unimi.dsi.fastutil.ints.IntOpenHashSet;

public class FastutilIntSetBasedRepository implements InMemoryRepository<Order> {
    private final IntOpenHashSet set = new IntOpenHashSet();

    @Override public void add(Order o) { set.add(o.id()); }
    @Override public boolean contains(Order o) { return set.contains(o.id()); }
    @Override public void remove(Order o) { set.remove(o.id()); }
}
