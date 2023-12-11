package org.example.repo;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BigDecimalsOps extends NumberOps<BigDecimal> {

    public BigDecimalsOps(List<BigDecimal> list) {
        super(list);
    }

    @Override
    protected BigDecimal zero() {
        return BigDecimal.ZERO;
    }

    @Override
    protected BigDecimal add(BigDecimal a, BigDecimal b) {
        return a.add(b);
    }

    @Override
    protected BigDecimal divide(BigDecimal a, int b) {
        return a.divide(BigDecimal.valueOf(b));
    }

    @Override
    protected Comparator<? super BigDecimal> comparator() {
        return Comparator.reverseOrder();
    }
}
