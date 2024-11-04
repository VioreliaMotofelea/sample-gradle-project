package lab2.repository;

import java.util.ArrayList;
import java.util.List;

public class ArrayListBasedRepository<T> implements InMemoryRepository<T> {
    private final List<T> list;

    public ArrayListBasedRepository() {
        this.list = new ArrayList<>();
    }

    public ArrayListBasedRepository(List<T> list) {
        this.list = list;
    }

    @Override
    public void add(T element) {
        list.add(element);
    }

    @Override
    public void remove(T element) {
        list.remove(element);
    }

    @Override
    public boolean contains(T element) {
        return list.contains(element);
    }
}
