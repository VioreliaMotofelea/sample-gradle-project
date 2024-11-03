package com.example.repository;

public interface InMemoryRepository<T> {
  void add(T item);
  boolean contains(T item);
  void remove(T item);
}
