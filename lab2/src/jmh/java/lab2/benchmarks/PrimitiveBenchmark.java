package lab2.benchmarks;

import com.koloboke.collect.set.IntSet;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class PrimitiveBenchmark {
    @Param({"10", "100", "1000", "10000", "100000"})
    public int size;

    private final IntArrayList ecList = new IntArrayList();
    private final it.unimi.dsi.fastutil.ints.IntArrayList fuList = new it.unimi.dsi.fastutil.ints.IntArrayList();

    private final it.unimi.dsi.fastutil.ints.IntOpenHashSet fuSet = new it.unimi.dsi.fastutil.ints.IntOpenHashSet();

//    private final com.koloboke.collect.set.IntSet kSet = com.koloboke.collect.set.hash.HashIntSets.newMutableSet();
    // Koloboke pusca, nush de ce


    private final int testInt1 = size + 1;


    @Setup
    public void setup() {
        for (int i = 0; i < size; i++) {
            ecList.add(i);
            fuList.add(i);
            fuSet.add(i);
//            kSet.add(i);
        }
    }

//    @Benchmark
//    public void akSetAdd() {
//        kSet.add(testInt1);
//        kSet.add(testInt2);
//    }
//
//    @Benchmark
//    public void akSetRemove() {
//        kSet.removeInt(testInt1);
//        kSet.removeInt(testInt2);
//    }
//
//    @Benchmark
//    public void akSetContains() {
//        kSet.contains(testInt1);
//        kSet.contains(testInt2);
//    }

    @Benchmark
    public void ecListAdd() {
        ecList.add(testInt1);
    }

    @Benchmark
    public void fuListAdd() {
        fuList.add(testInt1);
    }


    @Benchmark
    public void fuSetAdd() {
        fuSet.add(testInt1);
    }

    @Benchmark
    public void ecListRemove() {
        ecList.remove(testInt1);
    }

    @Benchmark
    public void fuListRemove() {
        fuList.rem(testInt1);
    }


    @Benchmark
    public void fuSetRemove() {
        fuSet.remove(testInt1);
    }

    @Benchmark
    public void ecListContains() {
        ecList.contains(testInt1);
    }

    @Benchmark
    public void fuListContains() {
        fuList.contains(testInt1);
    }


    @Benchmark
    public void fuSetContains() {
        fuSet.contains(testInt1);
    }

}
