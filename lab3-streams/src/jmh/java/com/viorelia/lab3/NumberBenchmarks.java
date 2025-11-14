package com.viorelia.lab3;

import org.openjdk.jmh.annotations.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.DoubleStream;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Thread)
public class NumberBenchmarks {

    @Param({"bigdecimal", "doubleObj", "doublePrim"})
    String impl;

    @Param({"RANDOM", "ASC", "DESC"})
    String order;

    @Param({"1000000", "100000000"})
    int size;

    BigDecimal[] bigDecimals;
    Double[] doublesObj;
    double[] doublesPrim;

    @Setup(Level.Trial)
    public void setup() {
        Random rnd = new Random(42);

        doublesPrim = DoubleStream.generate(() -> rnd.nextDouble() * 1000)
                .limit(size)
                .toArray();

        if ("ASC".equals(order)) {
            Arrays.sort(doublesPrim);
        } else if ("DESC".equals(order)) {
            Arrays.sort(doublesPrim);
            for (int i = 0; i < doublesPrim.length / 2; i++) {
                double tmp = doublesPrim[i];
                doublesPrim[i] = doublesPrim[doublesPrim.length - 1 - i];
                doublesPrim[doublesPrim.length - 1 - i] = tmp;
            }
        }

        doublesObj = Arrays.stream(doublesPrim).boxed().toArray(Double[]::new);
        bigDecimals = Arrays.stream(doublesPrim)
                .mapToObj(d -> BigDecimal.valueOf(d))
                .toArray(BigDecimal[]::new);
    }

    @Benchmark
    public Object sum() {
        return switch (impl) {
            case "bigdecimal" -> Arrays.stream(bigDecimals)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            case "doubleObj" -> Arrays.stream(doublesObj)
                    .mapToDouble(Double::doubleValue)
                    .sum();
            case "doublePrim" -> Arrays.stream(doublesPrim).sum();
            default -> throw new IllegalStateException("Unknown impl " + impl);
        };
    }

    @Benchmark
    public double average() {
        return switch (impl) {
            case "bigdecimal" -> {
                BigDecimal sum = Arrays.stream(bigDecimals)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                yield sum.divide(BigDecimal.valueOf(bigDecimals.length))
                        .doubleValue();
            }
            case "doubleObj" -> Arrays.stream(doublesObj)
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);
            case "doublePrim" -> Arrays.stream(doublesPrim)
                    .average()
                    .orElse(0.0);
            default -> throw new IllegalStateException("Unknown impl " + impl);
        };
    }
}
