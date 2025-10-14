
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
    void additionShouldReturnCorrectResult() throws ValidationException {
        double result = evaluator.evaluate("1 + 2");
        assertEquals(3, result);
    }

    @Test
    void subtractionShouldReturnCorrectResult() throws ValidationException {
        double result = evaluator.evaluate("5 - 3");
        assertEquals(2, result);
    }

    @Test
    void multiplicationShouldReturnCorrectResult() throws ValidationException {
        double result = evaluator.evaluate("4 * 3");
        assertEquals(12, result);
    }

    @Test
    void divisionShouldReturnCorrectResult() throws ValidationException {
        double result = evaluator.evaluate("10 / 2");
        assertEquals(5, result);
    }


    @Test
    void minShouldReturnSmallerOperand() throws ValidationException {
        double result = evaluator.evaluate("min 10 3");
        assertEquals(3, result);
    }

    @Test
    void maxShouldReturnLargerOperand() throws ValidationException {
        double result = evaluator.evaluate("max 10 3");
        assertEquals(10, result);
    }




    @Test
    void combinedExpressionShouldRespectPrecedence() throws ValidationException {
        double result = evaluator.evaluate("1 + 2 * 3");
        assertEquals(7, result);
    }

    @Test
    void expressionWithMinMaxShouldReturnCorrectResult() throws ValidationException {
        double result = evaluator.evaluate("1 + min 5 2 * max 2 3");
        assertEquals(7, result);
    }


    @Test
    void invalidExpressionShouldThrowValidationException() {
        assertThrows(ValidationException.class, () -> evaluator.evaluate("+ 2 3"));
    }

    @Test
    void divisionByZeroShouldThrowArithmeticException() throws ValidationException {
        Exception ex = assertThrows(ArithmeticException.class, () -> evaluator.evaluate("5 / 0"));
        assertTrue(ex.getMessage().contains("divide by zero"));
    }


}
