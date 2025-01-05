package lab2.repository;

import java.util.Set;
import java.util.TreeSet;

public class TreeSetBasedRepository<T extends Comparable<T>> implements InMemoryRepository<T>{
    private final Set<T> set;

    public TreeSetBasedRepository() {
        this.set = new TreeSet<>();
    }

    public TreeSetBasedRepository(TreeSet<T> set) {
        this.set = set;
    }
    @Override
    public void add(T element) {
        this.set.add(element);
    }

    @Override
    public void remove(T element) {
        this.set.remove(element);
    }

    @Override
    public boolean contains(T element) {
        return this.set.contains(element);
    }
}
