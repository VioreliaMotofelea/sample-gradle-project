import com.example.OperationFactory;
import com.example.operations.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OperationFactoryTest {

    @Test
    void unknownOperand_shouldThrowIllegalArgumentException() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> OperationFactory.get("^"));
        assertTrue(ex.getMessage().contains("Unknown"));
    }

    @Test
    void plus_shouldReturnAddition() {
        assertInstanceOf(Addition.class, OperationFactory.get("+"));
    }

    @Test
    void minus_shouldReturnSubstraction() {
        assertInstanceOf(Substraction.class, OperationFactory.get("-"));
    }

    @Test
    void multiply_shouldReturnMultiplication() {
        assertInstanceOf(Multiplication.class, OperationFactory.get("*"));
    }

    @Test
    void divide_shouldReturnDivision() {
        assertInstanceOf(Division.class, OperationFactory.get("/"));
    }

    @Test
    void min_shouldReturnMin() {
        assertInstanceOf(Min.class, OperationFactory.get("min"));
    }

    @Test
    void max_shouldReturnMax() {
        assertInstanceOf(Max.class, OperationFactory.get("max"));
    }
}
