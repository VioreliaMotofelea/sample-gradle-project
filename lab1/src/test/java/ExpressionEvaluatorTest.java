
import com.example.ExpressionEvaluator;
import com.example.OperationFactory;
import com.example.validator.ValidationException;
import com.example.validator.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExpressionEvaluatorTest {

    private ExpressionEvaluator evaluator;

    @BeforeEach
    void setup() {
        Validator validator = new Validator();
        evaluator = new ExpressionEvaluator(validator);
    }


    @Test
    void addition_shouldReturnCorrectResult() throws ValidationException {
        double result = evaluator.evaluate("1 + 2");
        assertEquals(3, result);
    }

    @Test
    void subtraction_shouldReturnCorrectResult() throws ValidationException {
        double result = evaluator.evaluate("5 - 3");
        assertEquals(2, result);
    }

    @Test
    void multiplication_shouldReturnCorrectResult() throws ValidationException {
        double result = evaluator.evaluate("4 * 3");
        assertEquals(12, result);
    }

    @Test
    void division_shouldReturnCorrectResult() throws ValidationException {
        double result = evaluator.evaluate("10 / 2");
        assertEquals(5, result);
    }


    @Test
    void min_shouldReturnSmallerOperand() throws ValidationException {
        double result = evaluator.evaluate("min 10 3");
        assertEquals(3, result);
    }

    @Test
    void max_shouldReturnLargerOperand() throws ValidationException {
        double result = evaluator.evaluate("max 10 3");
        assertEquals(10, result);
    }




    @Test
    void combinedExpression_shouldRespectPrecedence() throws ValidationException {
        double result = evaluator.evaluate("1 + 2 * 3");
        assertEquals(7, result);
    }

    @Test
    void expressionWithMinMax_shouldReturnCorrectResult() throws ValidationException {
        double result = evaluator.evaluate("1 + min 5 2 * max 2 3");
        assertEquals(7, result);
    }


    @Test
    void invalidExpression_shouldThrowValidationException() {
        assertThrows(ValidationException.class, () -> evaluator.evaluate("+ 2 3"));
    }

    @Test
    void divisionByZero_shouldThrowArithmeticException() throws ValidationException {
        Exception ex = assertThrows(ArithmeticException.class, () -> evaluator.evaluate("5 / 0"));
        assertTrue(ex.getMessage().contains("divide by zero"));
    }


}
