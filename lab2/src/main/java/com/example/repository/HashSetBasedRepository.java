package com.example.repository;

import java.util.HashSet;
import java.util.Set;

public class HashSetBasedRepository<T> implements InMemoryRepository<T> {
  private final Set<T> set = new HashSet<>();

  public HashSetBasedRepository() {}

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
