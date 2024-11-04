package lab2.repository;

import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;

public class ECSetRepository<T> implements InMemoryRepository<T>{
    private final MutableSet<T> set;

    public ECSetRepository() {
        this.set = new UnifiedSet<>();
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
