package org.example.benchmarks;

import gnu.trove.list.array.TIntArrayList;
import it.unimi.dsi.fastutil.ints.AbstractIntList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
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
public class TroveIntPrimitiveBenchmark {

    @State(Scope.Benchmark)
    public static class Helper {

        TIntArrayList repo = new TIntArrayList();

        @Param({"1", "100"})
        public int size;

        @Setup(Level.Iteration)
        public void setup() {
            repo.clear();
            for (int i = 0; i < size; i++) {
                repo.add(i);
            }
        }
    }

    @Benchmark
    public void add(Helper helper) {
        for (int i = 0; i < helper.size; i++) {
            helper.repo.add(i);
        }
    }

    @Benchmark
    public void contains(Helper helper) {
        for (int i = 0; i < helper.size; i++) {
            helper.repo.contains(i);
        }
    }

    @Benchmark
    public void remove(Helper helper) {
        for (int i = 0; i < helper.size; i++) {
            helper.repo.remove(i);
        }
    }
}
