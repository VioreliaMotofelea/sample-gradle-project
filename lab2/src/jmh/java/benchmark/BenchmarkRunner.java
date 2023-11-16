package benchmark;

import org.example.repository.implementation.task0.ArrayListRepository;
import org.example.repository.implementation.task0.HashSetRepository;
import org.example.repository.implementation.task0.TreeSetRepository;
import org.example.repository.implementation.task1.*;
import org.example.repository.implementation.task2.FastutilRepository;
import org.example.repository.implementation.task2.KolobokeRepository;
import org.example.repository.implementation.task2.Trove4jRepository;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkRunner {
    public static void main2(String args[]) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(RepositoryBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
