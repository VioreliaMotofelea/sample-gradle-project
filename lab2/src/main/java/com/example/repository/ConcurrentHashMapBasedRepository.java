package com.example.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapBasedRepository<T> implements InMemoryRepository<T> {
  private final Map<Integer, T> map = new ConcurrentHashMap<>();

  public ConcurrentHashMapBasedRepository() {}

  @Override
  public void add(T item) {
    map.put(item.hashCode(), item);
  }

  @Override
  public boolean contains(T item) {
    return map.containsKey(item.hashCode());
  }

  @Override
  public void remove(T item) {
    map.remove(item.hashCode());
  }
}
