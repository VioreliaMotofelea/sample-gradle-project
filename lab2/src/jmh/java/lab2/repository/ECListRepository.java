package lab2.repository;


import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.list.mutable.FastList;

public class ECListRepository<T> implements InMemoryRepository<T>{
    private final MutableList<T> list;

    public ECListRepository() {
        this.list = new FastList<>();
    }

    public ECListRepository(FastList<T> list) {
        this.list = list;
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
