package lab2.repository;

public class FUSetRepository<T> implements InMemoryRepository<T>{
    private final it.unimi.dsi.fastutil.objects.ObjectSet<T> set;

    public FUSetRepository() {
        this.set = new it.unimi.dsi.fastutil.objects.ObjectOpenHashSet<>();
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
