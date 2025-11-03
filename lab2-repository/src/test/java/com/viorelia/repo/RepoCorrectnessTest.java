package com.viorelia.repo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InMemoryRepository correctness (no JMH)")
public class RepoCorrectnessTest {
    static Stream<Arguments> repos() {
        return Stream.of(
                Arguments.of("ArrayList", (Supplier<InMemoryRepository<Order>>) ArrayListBasedRepository::new),
                Arguments.of("HashSet",   (Supplier<InMemoryRepository<Order>>) HashSetBasedRepository::new),
                Arguments.of("TreeSet",   (Supplier<InMemoryRepository<Order>>) TreeSetBasedRepository::new),

                Arguments.of("EclipseSet", (Supplier<InMemoryRepository<Order>>) EclipseSetBasedRepository::new),
                Arguments.of("FastutilSet",(Supplier<InMemoryRepository<Order>>) FastutilSetBasedRepository::new),

                Arguments.of("TroveInt",  (Supplier<InMemoryRepository<Order>>) TroveIntSetBasedRepository::new)
                // Arguments.of("KolobokeInt",(Supplier<InMemoryRepository<Order>>) KolobokeIntSetBasedRepository::new)
        );
    }

    private static Order o(int id) {
        return new Order(id, id * 10, id * 100);
    }

    private static List<Order> sample() {
        return List.of(o(1), o(2), o(3), o(4), o(5));
    }

    @ParameterizedTest(name = "{0} - add/contains single")
    @MethodSource("repos")
    void addContainsSingle(String name, Supplier<InMemoryRepository<Order>> sup) {
        var repo = sup.get();
        var a = o(42);
        assertFalse(repo.contains(a));
        repo.add(a);
        assertTrue(repo.contains(a));
    }

    @ParameterizedTest(name = "{0} - bulk add and query")
    @MethodSource("repos")
    void bulkAddAndContains(String name, Supplier<InMemoryRepository<Order>> sup) {
        var repo = sup.get();
        var data = sample();
        data.forEach(repo::add);
        data.forEach(o -> assertTrue(repo.contains(o)));
        assertFalse(repo.contains(o(999)));
    }

    @ParameterizedTest(name = "{0} - remove existing & missing")
    @MethodSource("repos")
    void removeBehavior(String name, Supplier<InMemoryRepository<Order>> sup) {
        var repo = sup.get();
        var a = o(1); var b = o(2);
        repo.add(a);
        repo.add(b);

        repo.remove(a);
        assertFalse(repo.contains(a));
        assertTrue(repo.contains(b));

        repo.remove(a);
        assertFalse(repo.contains(a));
    }

    @ParameterizedTest(name = "{0} - mixed sequence")
    @MethodSource("repos")
    void mixedSequence(String name, Supplier<InMemoryRepository<Order>> sup) {
        var repo = sup.get();
        var a = o(10); var b = o(11); var c = o(12);

        repo.add(a);
        repo.add(b);
        assertTrue(repo.contains(a));
        assertTrue(repo.contains(b));

        repo.remove(a);
        assertFalse(repo.contains(a));
        assertTrue(repo.contains(b));

        repo.add(c);
        assertTrue(repo.contains(c));
        assertFalse(repo.contains(a));
        assertTrue(repo.contains(b));
    }
}
