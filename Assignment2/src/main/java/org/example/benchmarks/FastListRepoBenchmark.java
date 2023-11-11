package org.example.benchmarks;

import org.example.repo.Order;
import org.example.repository.FastListBasedRepo;
import org.example.repository.InMemoryRepository;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class FastListRepoBenchmark {

    @State(Scope.Benchmark)
    public static class Helper {
        InMemoryRepository<Order> repo = new FastListBasedRepo<>();
        Order order = new Order(1, 1, 1);
        @Param({"1", "100"})
        public int size;

        @Setup(Level.Iteration)
        public void setup() {
            repo.clear();
            for (int i = 0; i < size; i++) {
                repo.add(order);
            }
        }
    }

    @Benchmark
    public void add(Helper helper) {
        for (int i = 0; i < helper.size; i++) {
            helper.repo.add(helper.order);
        }
    }

    @Benchmark
    public void contains(Helper helper) {
        for (int i = 0; i < helper.size; i++) {
            helper.repo.contains(helper.order);
        }
    }

    @Benchmark
    public void remove(Helper helper) {
        for (int i = 0; i < helper.size; i++) {
            helper.repo.remove(helper.order);
        }
    }
}
