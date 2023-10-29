package org.example.repository;

public interface InMemoryRepository<T> {
    void add(T item);
    void remove(T item);
    boolean contains(T item);
}
