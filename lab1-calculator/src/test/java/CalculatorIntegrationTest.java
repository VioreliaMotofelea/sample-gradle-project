import com.example.Evaluator;
import com.example.Parser;
import com.example.SimpleEvaluator;
import com.example.SimpleParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculatorIntegrationTest {
  private final Parser parser = new SimpleParser();
  private final Evaluator evaluator = new SimpleEvaluator();

  @Test
  void endToEndSimpleCases() {
    Assertions.assertEquals(3,   evaluator.evaluate(parser.parse("1+2")));
    Assertions.assertEquals(7,   evaluator.evaluate(parser.parse("10-3")));
    Assertions.assertEquals(56,  evaluator.evaluate(parser.parse("7*8")));
    Assertions.assertEquals(4,   evaluator.evaluate(parser.parse("20/5")));
  }

  @Test
  void endToEndWithSpacesAndUnaryMinus() {
    Assertions.assertEquals(-36, evaluator.evaluate(parser.parse("  -12  *  3 ")));
    Assertions.assertEquals(-1,  evaluator.evaluate(parser.parse("1+-2")));
  }
}
