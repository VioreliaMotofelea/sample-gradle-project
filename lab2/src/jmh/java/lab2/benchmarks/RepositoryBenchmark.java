package lab2.benchmarks;

import lab2.repository.*;
import org.openjdk.jmh.annotations.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class RepositoryBenchmark {
    @Param({"HashSet", "ArrayList", "TreeSet", "ConcHashMap", "ECList", "ECSet", "ECSortedSet", "FUList", "FUSet", "FUSortedSet"})
    private String type;

    private InMemoryRepository<Order> repository;

    @Param({"10", "100", "1000", "10000", "100000"})
    private int size;
    private final Random random = new Random();

    private Order testOrder = new Order(size * 2, 1, 1);

    private Order testOrder2 = new Order(size * 3, 1, 1);
    private Order testOrder3 = new Order(size - 2, 1, 1);

    @Setup
    public void setup() {
        repository = switch (type) {
            case "HashSet" -> new HashSetBasedRepository<>();
            case "ArrayList" -> new ArrayListBasedRepository<>();
            case "TreeSet" -> new TreeSetBasedRepository<>();
            case "ConcHashMap" -> new ConcurrentHashMapRepository<>();
            case "ECList" -> new ECListRepository<>();
            case "ECSet" -> new ECSetRepository<>();
            case "ECSortedSet" -> new ECSortedSetRepository<>();
            case "FUList" -> new FUListRepository<>();
            case "FUSet" -> new FUSetRepository<>();
            case "FUSortedSet" -> new FUSortedSetRepository<>();
//            case "KSet" -> new KSetRepository<>();
//            case "KMap" -> new KMapRepository<>();
            // Koloboke pusca, nush de ce
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
        for (int i = 0; i < size; i++) {
            repository.add(new Order(i, random.nextInt(1000), random.nextInt(1000)));
        }
    }

    @Benchmark
    public void add() {
        repository.add(testOrder);
    }

    @Benchmark
    public void remove() {
        repository.remove(testOrder2);
    }

    @Benchmark
    public void contains() {
        repository.contains(testOrder3);
    }

}
