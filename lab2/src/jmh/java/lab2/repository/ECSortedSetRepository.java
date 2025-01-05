package lab2.repository;

import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.impl.set.sorted.mutable.TreeSortedSet;

public class ECSortedSetRepository<T extends Comparable<T>> implements InMemoryRepository<T>{
    private final MutableSortedSet<T> set;

    public ECSortedSetRepository() {
        this.set = new TreeSortedSet<>();
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
