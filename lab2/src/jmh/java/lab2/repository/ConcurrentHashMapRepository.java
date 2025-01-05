package lab2.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapRepository<T> implements InMemoryRepository<T>{
    private final Map<Integer, T> map;

    public ConcurrentHashMapRepository() {
        this.map = new ConcurrentHashMap<>();
    }
    @Override
    public void add(T element) {
        this.map.put(element.hashCode(), element);
    }

    @Override
    public void remove(T element) {
        this.map.remove(element.hashCode());
    }

    @Override
    public boolean contains(T element) {
        return this.map.containsKey(element.hashCode());
    }
}
