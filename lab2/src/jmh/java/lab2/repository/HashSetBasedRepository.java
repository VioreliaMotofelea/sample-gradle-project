package lab2.repository;

import java.util.HashSet;
import java.util.Set;

public class HashSetBasedRepository<T>  implements InMemoryRepository<T>{
    private final Set<T> set;

    public HashSetBasedRepository() {
        this.set = new HashSet<>();
    }

    public HashSetBasedRepository(HashSet<T> set) {
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
