package com.viorelia.repo;

import gnu.trove.set.hash.TIntHashSet;

public class TroveIntSetBasedRepository implements InMemoryRepository<Order> {
    private final TIntHashSet set = new TIntHashSet();

    @Override public void add(Order o) { set.add(o.id()); }
    @Override public boolean contains(Order o) { return set.contains(o.id()); }
    @Override public void remove(Order o) { set.remove(o.id()); }
}
