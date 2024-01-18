package benchmark;

import org.example.utils.PrimitiveDoubleUtils;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class PrimitiveDoubleUtilsBenchmarkRunner {
    @Param({
            "1000",
            "1000000",
            "1000000000"
    })
    private String sizeStr;
    @Param({
            "random",
            "ascending",
            "descending"
    })
    private String orderStr;

    private double[] numbers;

    @Setup
    public void setup() {
        switch (orderStr) {
            case "random":
                numbers = PrimitiveDoubleUtils.generateRandomBigDecimalList(Integer.parseInt(sizeStr));
                break;
            case "ascending":
                numbers = PrimitiveDoubleUtils.generateSortedBigDecimalList(Integer.parseInt(sizeStr), true);
                break;
            case "descending":
                numbers = PrimitiveDoubleUtils.generateSortedBigDecimalList(Integer.parseInt(sizeStr), false);
                break;
        }
    }

    @Benchmark
    public void sum() {
        PrimitiveDoubleUtils.sum(numbers);
    }

    @Benchmark
    public void sumParallel() {
        PrimitiveDoubleUtils.sumParallel(numbers);
    }

    @Benchmark
    public void average() {
        PrimitiveDoubleUtils.average(numbers);
    }

    @Benchmark
    public void averageParallel() {
        PrimitiveDoubleUtils.averageParallel(numbers);
    }

    @Benchmark
    public void getTop10Percent() {
        PrimitiveDoubleUtils.getTop10Percent(numbers);
    }
}
