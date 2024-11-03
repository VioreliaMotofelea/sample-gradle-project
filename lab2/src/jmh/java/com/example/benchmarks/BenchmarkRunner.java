package com.example.benchmarks;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.nio.file.Path;

public class BenchmarkRunner {
    public static void main(String[] args) throws RunnerException {
        var options = new OptionsBuilder()
            .include(RepositoryBenchmark.class.getSimpleName())
            .output(Path.of("lab2" ,"src", "jmh", "resources", "results.txt").toString())
            .forks(1)
            .build();

        new Runner(options).run();
    }
}
