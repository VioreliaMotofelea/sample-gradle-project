import com.example.OperationFactory;
import com.example.operations.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OperationFactoryTest {

    @Test
    void unknownOperandShouldThrowIllegalArgumentException() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> OperationFactory.getOp("^"));
        assertTrue(ex.getMessage().contains("Unknown"));
    }

    @Test
    void plusShouldReturnAddition() {
        assertInstanceOf(Addition.class, OperationFactory.getOp("+"));
    }

    @Test
    void minusShouldReturnSubstraction() {
        assertInstanceOf(Substraction.class, OperationFactory.getOp("-"));
    }

    @Test
    void multiplyShouldReturnMultiplication() {
        assertInstanceOf(Multiplication.class, OperationFactory.getOp("*"));
    }

    @Test
    void divideShouldReturnDivision() {
        assertInstanceOf(Division.class, OperationFactory.getOp("/"));
    }

    @Test
    void minShouldReturnMin() {
        assertInstanceOf(Min.class, OperationFactory.getOp("min"));
    }

    @Test
    void maxShouldReturnMax() {
        assertInstanceOf(Max.class, OperationFactory.getOp("max"));
    }
}
