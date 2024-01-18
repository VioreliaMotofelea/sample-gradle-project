package benchmark;

import org.example.utils.BigDecimalUtils;
import org.openjdk.jmh.annotations.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 20, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class BigDecimalUtilsBenchmarkRunner {
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

    private List<BigDecimal> numbers;

    @Setup
    public void setup() {
        switch (orderStr) {
            case "random":
                numbers = BigDecimalUtils.generateRandomBigDecimalList(Integer.parseInt(sizeStr));
                break;
            case "ascending":
                numbers = BigDecimalUtils.generateSortedBigDecimalList(Integer.parseInt(sizeStr), true);
                break;
            case "descending":
                numbers = BigDecimalUtils.generateSortedBigDecimalList(Integer.parseInt(sizeStr), false);
                break;
        }
    }

    @Benchmark
    public void sum() {
        BigDecimalUtils.sum(numbers);
    }

    @Benchmark
    public void sumParallel() {
        BigDecimalUtils.sumParallel(numbers);
    }

    @Benchmark
    public void average() {
        BigDecimalUtils.average(numbers);
    }

    @Benchmark
    public void averageParallel() {
        BigDecimalUtils.averageParallel(numbers);
    }

    @Benchmark
    public void getTop10Percent() {
        BigDecimalUtils.getTop10Percent(numbers);
    }
}
