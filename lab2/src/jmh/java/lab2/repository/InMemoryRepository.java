package lab2.repository;

public interface InMemoryRepository<T> {
    void add(T element);
    void remove(T element);
    boolean contains(T element);
}
