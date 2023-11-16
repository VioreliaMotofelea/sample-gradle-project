package org.example.repository._interface;

import org.example.entity.Order;

public interface IInMemoryRepository<T extends Comparable<T>> {
    void add(T entity);
    void remove(T entity);
    boolean contains(T entity);
}
