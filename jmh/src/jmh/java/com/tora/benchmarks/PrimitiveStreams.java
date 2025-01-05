package com.tora.benchmarks;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.tora.StreamOperations.BigDecimalOperation;
import com.tora.StreamOperations.DoubleObjectOperation;
import com.tora.StreamOperations.DoublePrimitiveOperation;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;


@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class PrimitiveStreams {
    @Param({"10000", "1000000", "100000000"})
    int size;

    @Param({"Random", "Sorted", "ReverseSorted"})
    String type;

    @Param({"BigDecimal", "DoubleObject", "DoublePrimitive"})
    String operation;


    List<BigDecimal> BigDecimalList;
    List<Double> DoubleList;
    double[] doubleArray;

    BigDecimalOperation bigDecimalOperation;

    DoubleObjectOperation doubleObjectOperation;

    DoublePrimitiveOperation doublePrimitiveOperation;

    @Setup
    public void setup() {
        bigDecimalOperation = new BigDecimalOperation();
        doubleObjectOperation = new DoubleObjectOperation();
        doublePrimitiveOperation = new DoublePrimitiveOperation();
        switch (type){
            case "Random":
                switch (operation)
                {
                    case "BigDecimal":
                        BigDecimalList = Stream.generate(() -> BigDecimal.valueOf(Math.random())).limit(size).collect(Collectors.toList());
                        break;
                    case "DoubleObject":
                        DoubleList = Stream.generate(() -> Math.random()).limit(size).collect(Collectors.toList());
                        break;
                    case "DoublePrimitive":
                        doubleArray = IntStream.range(0, size).mapToDouble(i -> Math.random()).toArray();
                        break;
                }
                break;
            case "Sorted":
                switch (operation)
                {
                    case "BigDecimal":
                        BigDecimal bDIncrement = BigDecimal.ONE.divide(BigDecimal.valueOf(size));
                        BigDecimalList = Stream.iterate(BigDecimal.ZERO, bd -> bd.add(bDIncrement)).limit(size).collect(Collectors.toList());
                        break;
                    case "DoubleObject":
                        double dPIncrement = 1.0 / size;
                        DoubleList = Stream.iterate(0.0, d -> d + dPIncrement).limit(size).collect(Collectors.toList());
                        break;
                    case "DoublePrimitive":
                        double dPIncrement1 = 1.0 / size;
                        doubleArray = IntStream.range(0, size).mapToDouble(i -> dPIncrement1).toArray();
                        break;
                }
                break;
            case "ReverseSorted":
                switch (operation)
                {
                    case "BigDecimal":
                        BigDecimal bDIncrement = BigDecimal.ONE.divide(BigDecimal.valueOf(size));
                        BigDecimalList = Stream.iterate(BigDecimal.ONE, bd -> bd.subtract(bDIncrement)).limit(size).collect(Collectors.toList());
                        break;
                    case "DoubleObject":
                        double dPIncrement = 1.0 / size;
                        DoubleList = Stream.iterate(1.0, d -> d - dPIncrement).limit(size).collect(Collectors.toList());
                        break;
                    case "DoublePrimitive":
                        double dPIncrement1 = 1.0 / size;
                        doubleArray = IntStream.range(0, size).mapToDouble(i -> dPIncrement1).toArray();
                        break;
                }
                break;

        }
    }

    @Benchmark
    public void testAddList(Blackhole bh) {
        switch (operation)
        {
            case "BigDecimal":
                bh.consume(bigDecimalOperation.addList(BigDecimalList));
                break;
            case "DoubleObject":
                bh.consume(doubleObjectOperation.addList(DoubleList));
                break;
            case "DoublePrimitive":
                bh.consume(doublePrimitiveOperation.addList(doubleArray));
                break;
        }
    }

    @Benchmark
    public void testAverageOfList(Blackhole bh) {
        switch (operation)
        {
            case "BigDecimal":
                bh.consume(bigDecimalOperation.averageOfList(BigDecimalList));
                break;
            case "DoubleObject":
                bh.consume(doubleObjectOperation.averageOfList(DoubleList));
                break;
            case "DoublePrimitive":
                bh.consume(doublePrimitiveOperation.averageOfList(doubleArray));
                break;
        }
    }

    @Benchmark
    public void testTop10OfList(Blackhole bh) {
        switch (operation)
        {
            case "BigDecimal":
                bh.consume(bigDecimalOperation.top10OfList(BigDecimalList));
                break;
            case "DoubleObject":
                bh.consume(doubleObjectOperation.top10OfList(DoubleList));
                break;
            case "DoublePrimitive":
                bh.consume(doublePrimitiveOperation.top10NotOfList(doubleArray));
                break;
        }
    }

}
