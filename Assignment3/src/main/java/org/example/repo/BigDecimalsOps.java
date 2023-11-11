package org.example.repo;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BigDecimalsOps {

    private List<BigDecimal> bigDecimalList;

    public BigDecimalsOps(List<BigDecimal> list) {
        bigDecimalList = list;
    }

    public BigDecimal sum() {
        return bigDecimalList.stream().reduce(BigDecimal.valueOf(0), BigDecimal::add);
    }

    public BigDecimal average() {
        return sum().divide(BigDecimal.valueOf(bigDecimalList.size()));
    }

    public List<BigDecimal> top10perc() {
        int tenPerc = bigDecimalList.size() / 10;
        return bigDecimalList.stream()
                .sorted(Comparator.reverseOrder())
                .limit(tenPerc)
                .collect(Collectors.toList());
    }
}
