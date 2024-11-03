package com.example.repository;
import java.util.ArrayList;
import java.util.List;

public class ArrayListBasedRepository<T> implements InMemoryRepository<T> {
  private final List<T> list = new ArrayList<T>();

  public ArrayListBasedRepository() {}

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
