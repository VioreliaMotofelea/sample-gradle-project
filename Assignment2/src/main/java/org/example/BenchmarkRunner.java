package org.example;

import org.example.benchmarks.ArrayListRepoBenchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
//        Options opt = new OptionsBuilder()
//                .include(ArrayListRepoBenchmark.class.getSimpleName())
//                .forks(1)
//                .build();
//
//        new Runner(opt).run();

    }
}
