package lab2.repository;

public class FUSortedSetRepository<T extends Comparable<T>> implements InMemoryRepository<T>{
    private final it.unimi.dsi.fastutil.objects.ObjectSortedSet<T> set;

    public FUSortedSetRepository() {
        this.set = new it.unimi.dsi.fastutil.objects.ObjectRBTreeSet<>();
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
