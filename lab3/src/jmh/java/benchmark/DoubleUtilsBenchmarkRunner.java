package benchmark;

import org.example.utils.DoubleUtils;
import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class DoubleUtilsBenchmarkRunner {
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

    private List<Double> numbers;

    @Setup
    public void setup() {
        switch (orderStr) {
            case "random":
                numbers = DoubleUtils.generateRandomBigDecimalList(Integer.parseInt(sizeStr));
                break;
            case "ascending":
                numbers = DoubleUtils.generateSortedBigDecimalList(Integer.parseInt(sizeStr), true);
                break;
            case "descending":
                numbers = DoubleUtils.generateSortedBigDecimalList(Integer.parseInt(sizeStr), false);
                break;
        }
    }

    @Benchmark
    public void sum() {
        DoubleUtils.sum(numbers);
    }

    @Benchmark
    public void sumParallel() {
        DoubleUtils.sumParallel(numbers);
    }

    @Benchmark
    public void average() {
        DoubleUtils.average(numbers);
    }

    @Benchmark
    public void averageParallel() {
        DoubleUtils.averageParallel(numbers);
    }

    @Benchmark
    public void getTop10Percent() {
        DoubleUtils.getTop10Percent(numbers);
    }
}
