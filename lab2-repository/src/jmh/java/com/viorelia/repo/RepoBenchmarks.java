package com.viorelia.repo;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class RepoBenchmarks {

    @Param({"1000", "10000", "50000"})
    int size;

    @Param({"arraylist", "hashset", "treeset", "concurrent",
            "ec", "fastutil",
            "ec-int", "fastutil-int", "trove-int" /*, "koloboke-int"*/ })
    String impl;

    InMemoryRepository<Order> repo;
    List<Order> existing;
    List<Order> missing;

    @Setup(Level.Trial)
    public void setup() {
        repo = switch (impl) {
            case "arraylist" -> new ArrayListBasedRepository<>();
            case "hashset" -> new HashSetBasedRepository<>();
            case "treeset" -> new TreeSetBasedRepository<>();
            case "concurrent" -> new ConcurrentHashMapBasedRepository<>();
            case "ec" -> new EclipseSetBasedRepository<>();
            case "ec-int" -> new EclipseIntSetBasedRepository();
            case "fastutil" -> new FastutilSetBasedRepository<>();
            case "fastutil-int" -> new FastutilIntSetBasedRepository();
            case "trove-int" -> new TroveIntSetBasedRepository();
            //case "koloboke-int" -> new KolobokeIntSetBasedRepository();
            default -> throw new IllegalArgumentException("Unknown impl: " + impl);
        };

        existing = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Order o = new Order(i, i * 10, 1 + (i % 5));
            existing.add(o);
            repo.add(o);
        }

        missing = new ArrayList<>(size);
        for (int i = size; i < size * 2; i++) {
            missing.add(new Order(i, i * 10, 1 + (i % 5)));
        }
    }

    int c1, c2;

    @Benchmark
    public void add() {
        repo.add(missing.get((c1++) % missing.size()));
    }

    @Benchmark
    public boolean contains_hit() {
        return repo.contains(existing.get((c2++) % existing.size()));
    }

    @Benchmark
    public boolean contains_miss() {
        return repo.contains(missing.get((c2++) % missing.size()));
    }

    @Benchmark
    public void remove_existing() {
        if (!existing.isEmpty()) {
            Order o = existing.remove(existing.size() - 1);
            repo.remove(o);
        }
    }
}
