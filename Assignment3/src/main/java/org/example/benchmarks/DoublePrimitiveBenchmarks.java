package org.example.benchmarks;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import org.example.repo.DoubleOps;
import org.example.repo.DoublePrimitiveOps;
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
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class DoublePrimitiveBenchmarks {

    @State(Scope.Benchmark)
    public static class Helper {
        DoublePrimitiveOps doubleOps;
        DoubleArrayList doubleList = new DoubleArrayList();

        @Param({"100", "100000000"})
        public int size;

        @Setup(Level.Invocation)
        public void setup() {
            doubleList.clear();

            for (int i = 0; i < size; i++) {
                doubleList.add(i);
            }

            doubleOps = new DoublePrimitiveOps(doubleList);
            doubleList.clear();
        }
    }

    @Benchmark
    public void sum(Helper helper, Blackhole consumer) {
        consumer.consume(helper.doubleOps.sum());
    }

    @Benchmark
    public void average(Helper helper, Blackhole consumer) {
        consumer.consume(helper.doubleOps.average());
    }

    @Benchmark
    public void top10perc(Helper helper, Blackhole consumer) {
        consumer.consume(helper.doubleOps.top10perc());
    }
}
