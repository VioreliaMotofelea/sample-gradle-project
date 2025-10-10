import com.example.BinaryExpression;
import com.example.Evaluator;
import com.example.Operator;
import com.example.SimpleEvaluator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimpleEvaluatorTest {
  private final Evaluator evaluator = new SimpleEvaluator();

  @Test
  void evaluatesAddSubMulDiv() {
    Assertions.assertEquals(3, evaluator.evaluate(new BinaryExpression(1, Operator.ADD, 2)));
    Assertions.assertEquals(-1, evaluator.evaluate(new BinaryExpression(1, Operator.SUB, 2)));
    Assertions.assertEquals(6, evaluator.evaluate(new BinaryExpression(2, Operator.MUL, 3)));
    Assertions.assertEquals(2, evaluator.evaluate(new BinaryExpression(4, Operator.DIV, 2)));
  }

  @Test
  void throwsExceptionOnDivideByZero() {
    Assertions.assertThrows(ArithmeticException.class,
      () -> evaluator.evaluate(new BinaryExpression(1, Operator.DIV, 0))
    );
  }

  @Test
  void evaluatesWithNegativeOperands() {
    Assertions.assertEquals(-5, evaluator.evaluate(new BinaryExpression(-3, Operator.ADD, -2)));
    Assertions.assertEquals(-6, evaluator.evaluate(new BinaryExpression(2, Operator.MUL, -3)));
  }
}
