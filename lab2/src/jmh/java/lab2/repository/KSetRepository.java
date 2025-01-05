package lab2.repository;

public class KSetRepository<T> implements InMemoryRepository<T>{
    private final com.koloboke.collect.set.ObjSet<T> list;

    public KSetRepository() {
        this.list = com.koloboke.collect.set.hash.HashObjSets.newMutableSet();
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
