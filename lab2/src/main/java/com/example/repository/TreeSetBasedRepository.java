package com.example.repository;

import java.util.Set;
import java.util.TreeSet;


public class TreeSetBasedRepository<T extends Comparable<T>> implements InMemoryRepository<T> {
  private final Set<T> set = new TreeSet<>();

  public TreeSetBasedRepository() {}

  @Override
  public void add(T item) {
    set.add(item);
  }

  @Override
  public boolean contains(T item) {
    return set.contains(item);
  }

  @Override
  public void remove(T item) {
    set.remove(item);
  }
}
