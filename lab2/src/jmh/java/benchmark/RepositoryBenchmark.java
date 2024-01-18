package benchmark;

import org.example.repository._interface.IInMemoryRepository;
import org.example.repository.implementation.task0.ArrayListRepository;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class RepositoryBenchmark {
    @Param({"ArrayListRepository",
            "HashSetRepository",
            "TreeSetRepository",
            "ConcurrentHashMapRepository",
            "ConcurrentHashMapEclipseRepository",
            "FastListRepository",
            "UnifiedMapRepository",
            "UnifiedSetRepository",
            "FastutilRepository",
            "KolobokeRepository",
            "Trove4jRepository"})
    public String repositoryClassName;

    private IInMemoryRepository<Integer> repository;
    private int ops = 0;

    @Setup
    public void setup() {
        switch (repositoryClassName) {
            case "ArrayListRepository":
                repository = new ArrayListRepository<Integer>();
                break;
            case "HashSetRepository":
                repository = new org.example.repository.implementation.task0.HashSetRepository<Integer>();
                break;
            case "TreeSetRepository":
                repository = new org.example.repository.implementation.task0.TreeSetRepository<Integer>();
                break;

            case "ConcurrentHashMapRepository":
                repository = new org.example.repository.implementation.task1.ConcurrentHashMapRepository<Integer>();
                break;
            case "ConcurrentHashMapEclipseRepository":
                repository = new org.example.repository.implementation.task1.ConcurrentHashMapEclipseRepository<Integer>();
                break;
            case "FastListRepository":
                repository = new org.example.repository.implementation.task1.FastListRepository<Integer>();
                break;
            case "UnifiedMapRepository":
                repository = new org.example.repository.implementation.task1.UnifiedMapRepository<Integer>();
                break;
            case "UnifiedSetRepository":
                repository = new org.example.repository.implementation.task1.UnifiedSetRepository<Integer>();
                break;

            case "FastutilRepository":
                repository = new org.example.repository.implementation.task2.FastutilRepository();
                break;
            case "KolobokeRepository":
                repository = new org.example.repository.implementation.task2.KolobokeRepository();
                break;
            case "Trove4jRepository":
                repository = new org.example.repository.implementation.task2.Trove4jRepository();
                break;

            default:
                throw new IllegalArgumentException("Unknown repository class name: " + repositoryClassName);
        }
        System.out.println("Benchmarking " + repositoryClassName);

        for (int i = 0; i < 10240; i++) {
            this.add();
        }
    }

    @Benchmark
    public void add() {
        repository.add(ops++);
    }

    @Benchmark
    public void remove() {
        repository.remove(ops + 1);
        repository.remove(--ops);
    }

    @Benchmark
    public void contains() {
        repository.contains(ops - 1);
        repository.contains(ops + 1);
    }
}
