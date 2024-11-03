package com.example.repository;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.list.mutable.FastList;

public class EclipseMutableListBasedRepository<T> implements InMemoryRepository<T> {
  private final MutableList<T> list = new FastList<>();

  public EclipseMutableListBasedRepository() {}

  @Override
  public void add(T item) {
    list.add(item);
  }

  @Override
  public boolean contains(T item) {
    return list.contains(item);
  }

  @Override
  public void remove(T item) {
    list.remove(item);
  }
}
