package lab2.repository;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
public class FUListRepository<T> implements InMemoryRepository<T>{
    private final ObjectList<T> list;

    public FUListRepository() {
        this.list = new ObjectArrayList<>();
    }

    @Override
    public void add(T element) {
        this.list.add(element);
    }

    @Override
    public void remove(T element) {
        this.list.remove(element);
    }

    @Override
    public boolean contains(T element) {
        return this.list.contains(element);
    }
}
