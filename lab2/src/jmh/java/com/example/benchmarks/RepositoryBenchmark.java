package com.example.benchmarks;

import com.example.model.Order;
import com.example.repository.*;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class RepositoryBenchmark {
  @Param({"ArrayListBasedRepository", "HashSetBasedRepository", "TreeSetBasedRepository",
      "ConcurrentHashMapBasedRepository", "EclipseMutableListBasedRepository",
      "FastutilObject2ObjectOpenHashMapBasedRepository", "KolobokeHashObjObjMapBasedRepository"})
  private String repositoryType;
  @Param({"1", "31", "65", "101", "103", "1024", "10240", "65535"}) // add also "21474836"
  public int size;

  private InMemoryRepository<Order> repository;
  private Order[] orders;

  @Setup(Level.Trial)
  public void setUp(){
    switch (repositoryType) {
      case "ArrayListBasedRepository":
        repository = new ArrayListBasedRepository<>();
        break;
      case "HashSetBasedRepository":
        repository = new HashSetBasedRepository<>();
        break;
      case "TreeSetBasedRepository":
        repository = new TreeSetBasedRepository<>();
        break;
      case "ConcurrentHashMapBasedRepository":
        repository = new ConcurrentHashMapBasedRepository<>();
        break;
      case "EclipseMutableListBasedRepository":
        repository = new EclipseMutableListBasedRepository<>();
        break;
      case "FastutilObject2ObjectOpenHashMapBasedRepository":
        repository = new FastutilObject2ObjectOpenHashMapBasedRepository<>();
        break;
      case "KolobokeHashObjObjMapBasedRepository":
        repository = new KolobokeHashObjObjMapBasedRepository<>();
        break;
      default:
        throw new IllegalArgumentException("Unknown repository type: " + repositoryType);
    }

    orders = new Order[size + 1];
    IntStream.rangeClosed(0, size)
        .forEach(i -> orders[i] = new Order(i, i*10, i*100));
  }

  @Benchmark
  public void add(Blackhole consumer) {
    IntStream.rangeClosed(0, size).forEach(i -> {
      repository.add(orders[i]);
      consumer.consume(orders[i]);
    });
  }

  @Benchmark
  public void contains(Blackhole consumer) {
    IntStream.rangeClosed(0, size).forEach(i -> {
      repository.contains(orders[i]);
      consumer.consume(orders[i]);
    });
  }

  @Benchmark
  public void remove(Blackhole consumer) {
    IntStream.rangeClosed(0, size).forEach(i -> {
      repository.remove(orders[i]);
      consumer.consume(orders[i]);
    });
  }
}
