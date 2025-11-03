//package com.viorelia.repo;
//
//import com.koloboke.collect.set.IntSet;
//import com.koloboke.collect.set.hash.HashIntSets;
//
//public class KolobokeIntSetBasedRepository implements InMemoryRepository<Order> {
//    private final IntSet set = HashIntSets.newMutableSet();
//
//    @Override public void add(Order o) { set.add(o.id()); }
//    @Override public boolean contains(Order o) { return set.contains(o.id()); }
//    @Override public void remove(Order o) { set.removeInt(o.id()); }
//}
