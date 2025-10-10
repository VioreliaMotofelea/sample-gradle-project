import com.example.BinaryExpression;
import com.example.Operator;
import com.example.Parser;
import com.example.SimpleParser;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class SimpleParserTest {
  private final Parser parser = new SimpleParser();

  @Test
  void parsesSimpleAdd() {
    BinaryExpression e = parser.parse("1+2");
    assertEquals(1, e.getLeftOperand());
    assertEquals(Operator.ADD, e.getOperator());
    assertEquals(2, e.getRightOperand());
  }

  @Test
  void parsesWithSpaces() {
    BinaryExpression e = parser.parse("  10   -   3 ");
    assertEquals(10, e.getLeftOperand());
    assertEquals(Operator.SUB, e.getOperator());
    assertEquals(3, e.getRightOperand());
  }

  @Test
  void parsesUnaryMinusRight() {
    BinaryExpression e = parser.parse("5*-2");
    assertEquals(5, e.getLeftOperand());
    assertEquals(Operator.MUL, e.getOperator());
    assertEquals(-2, e.getRightOperand());
  }

  @Test
  void parsesUnaryMinusLeft() {
    BinaryExpression e = parser.parse("-7/2");
    assertEquals(-7, e.getLeftOperand());
    assertEquals(Operator.DIV, e.getOperator());
    assertEquals(2, e.getRightOperand());
  }
}
