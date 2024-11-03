package com.example.repository;

//import gnu.trove.set.hash.THashSet;

public class Trove4HashSetBasedRepository<T> implements InMemoryRepository<T> {
  //private final THashSet<T> set = new THashSet<>();

  public Trove4HashSetBasedRepository() {}

  @Override
  public void add(T item) {
    //set.add(item);
  }

  @Override
  public boolean contains(T item) {
    return false;
    //return set.contains(item);
  }

  @Override
  public void remove(T item) {
    //set.remove(item);
  }
}
