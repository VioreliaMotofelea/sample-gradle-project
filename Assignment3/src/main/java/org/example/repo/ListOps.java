package org.example.repo;

import java.util.List;

public interface ListOps<T> {
    public T add();
    public T average();

    public List<T> top10perc();
}
