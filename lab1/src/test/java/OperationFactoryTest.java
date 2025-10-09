import com.example.OperationFactory;
import com.example.operations.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OperationFactoryTest {

    @Test
    void unknownOperandShouldThrowIllegalArgumentException() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> OperationFactory.get("^"));
        assertTrue(ex.getMessage().contains("Unknown"));
    }

    @Test
    void plusShouldReturnAddition() {
        assertInstanceOf(Addition.class, OperationFactory.get("+"));
    }

    @Test
    void minusShouldReturnSubstraction() {
        assertInstanceOf(Substraction.class, OperationFactory.get("-"));
    }

    @Test
    void multiplyShouldReturnMultiplication() {
        assertInstanceOf(Multiplication.class, OperationFactory.get("*"));
    }

    @Test
    void divideShouldReturnDivision() {
        assertInstanceOf(Division.class, OperationFactory.get("/"));
    }

    @Test
    void minShouldReturnMin() {
        assertInstanceOf(Min.class, OperationFactory.get("min"));
    }

    @Test
    void maxShouldReturnMax() {
        assertInstanceOf(Max.class, OperationFactory.get("max"));
    }
}
