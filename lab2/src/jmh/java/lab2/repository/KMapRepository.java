package lab2.repository;

public class KMapRepository<T extends Comparable<T>> implements InMemoryRepository<T>{
    private final com.koloboke.collect.map.IntObjMap<T> list;

    public KMapRepository() {
        this.list = com.koloboke.collect.map.hash.HashIntObjMaps.newMutableMap();
    }
    @Override
    public void add(T element) {
        this.list.put(element.hashCode(), element);
    }

    @Override
    public void remove(T element) {
        this.list.remove(element.hashCode());
    }

    @Override
    public boolean contains(T element) {
        return this.list.containsKey(element.hashCode());
    }
}
