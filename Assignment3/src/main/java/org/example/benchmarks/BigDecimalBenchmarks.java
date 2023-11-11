package org.example.benchmarks;

import org.example.repo.BigDecimalsOps;
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class BigDecimalBenchmarks {

    @State(Scope.Benchmark)
    public static class Helper {
        BigDecimalsOps bigDecimalsOps;
        @Param({"100", "100000000"})
        public int size;

        @Setup(Level.Iteration)
        public void setup() {
            List<BigDecimal> bigDecimalList = new ArrayList<BigDecimal>();

            for (int i = 0; i < size; i++) {
                bigDecimalList.add(new BigDecimal(BigInteger.valueOf(i)));
            }

            bigDecimalsOps = new BigDecimalsOps(bigDecimalList);
        }
    }

    @Benchmark
    public void sum(Helper helper) {
        helper.bigDecimalsOps.sum();
    }

    @Benchmark
    public void average(Helper helper) {
        helper.bigDecimalsOps.average();
    }

    @Benchmark
    public void top10perc(Helper helper) {
        helper.bigDecimalsOps.top10perc();
    }
}
