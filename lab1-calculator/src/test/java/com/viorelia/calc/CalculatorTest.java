package com.viorelia.calc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    Calculator c = new Calculator();

    @Test void adds() { assertEquals(5, c.add(2,3), 1e-9); }
    @Test void subs() { assertEquals(-1, c.sub(2,3), 1e-9); }
    @Test void muls() { assertEquals(6, c.mul(2,3), 1e-9); }
    @Test void divs() { assertEquals(2, c.div(6,3), 1e-9); }
    @Test void divByZero() { assertThrows(ArithmeticException.class, () -> c.div(1,0)); }
    @Test void minMax() { assertEquals(2, c.min(2,3), 1e-9); assertEquals(3, c.max(2,3), 1e-9); }
    @Test void sqrtOk() { assertEquals(5, c.sqrt(25), 1e-9); }
    @Test void sqrtNeg() { assertThrows(IllegalArgumentException.class, () -> c.sqrt(-1)); }
}
