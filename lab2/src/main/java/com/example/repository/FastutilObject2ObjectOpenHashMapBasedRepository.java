package com.example.repository;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public class FastutilObject2ObjectOpenHashMapBasedRepository<T> implements InMemoryRepository<T> {
  private final Object2ObjectOpenHashMap<Integer, T> map = new Object2ObjectOpenHashMap<>();

  public FastutilObject2ObjectOpenHashMapBasedRepository() {}

  public void add(T item) {
    map.put(item.hashCode(), item);
  }
  public boolean contains(T item) {
    return map.containsKey(item.hashCode());
  }
  public void remove(T item) {
    map.remove(item.hashCode());
  }
}
