package labs.lab1;

import labs.lab1.model.operations.IOperation;
import labs.lab1.model.operations.OperationFactory;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

public class OperationsTest {

    @Test
    public void testUnsupportedOperation() {
        testUnsupportedOperation1();
    }

    private void testUnsupportedOperation1() {
        try {
            OperationFactory.createOperation("unsupported");

            assert false;
        } catch (UnsupportedOperationException ignored) {

        }
    }

    @Test
    public void testAddition() {
        testAddition1();
        testAddition2();
        testAddition3();
    }

    private void testAddition1() {
        try {
            OperationFactory.createOperation("+");

            assert false;
        } catch (InvalidParameterException ignored) {

        }
    }

    private void testAddition2() {
        IOperation operation = OperationFactory.createOperation("+", 1, 2);
        assert operation.calculate() == 3;
    }

    private void testAddition3() {
        IOperation operation = OperationFactory.createOperation("+", 1, 2, 3, 4, 5);
        assert operation.calculate() == 15;
    }

    @Test
    public void testSubtraction() {
        testSubtraction1();
        testSubtraction2();
        testSubtraction3();
    }

    private void testSubtraction1() {
        try {
            OperationFactory.createOperation("-");

            assert false;
        } catch (InvalidParameterException ignored) {

        }
    }

    private void testSubtraction2() {
        IOperation operation = OperationFactory.createOperation("-", 1, 2);
        assert operation.calculate() == -1;
    }

    private void testSubtraction3() {
        try {
            OperationFactory.createOperation("-", 1, 2, 3, 4, 5);

            assert false;
        } catch (InvalidParameterException ignored) {

        }
    }

    @Test
    public void testMultiplication() {
        testMultiplication1();
        testMultiplication2();
        testMultiplication3();
    }

    private void testMultiplication1() {
        try {
            OperationFactory.createOperation("*");

            assert false;
        } catch (InvalidParameterException ignored) {

        }
    }

    private void testMultiplication2() {
        IOperation operation = OperationFactory.createOperation("*", 1, 2);
        assert operation.calculate() == 2;
    }

    private void testMultiplication3() {
        IOperation operation = OperationFactory.createOperation("*", 1, 2, 3, 4, 5);
        assert operation.calculate() == 120;
    }

    @Test
    public void testDivision() {
        testDivision1();
        testDivision2();
        testDivision3();
    }

    private void testDivision1() {
        try {
            OperationFactory.createOperation("/", 1);

            assert false;
        } catch (InvalidParameterException ignored) {

        }
    }

    private void testDivision2() {
        IOperation operation = OperationFactory.createOperation("/", 1, 2);
        assert operation.calculate() == 0.5;
    }

    private void testDivision3() {
        try {
            OperationFactory.createOperation("/", 1, 2, 3, 4, 5);

            assert false;
        } catch (InvalidParameterException ignored) {

        }
    }

    @Test
    public void testModulo1() {
        try {
            OperationFactory.createOperation("%", 1);

            assert false;
        } catch (InvalidParameterException ignored) {

        }
    }

    @Test
    public void testModulo2() {
        IOperation operation = OperationFactory.createOperation("%", 1, 2);
        assert operation.calculate() == 1;
    }

    @Test
    public void testModulo3() {
        try {
            OperationFactory.createOperation("%", 1, 2, 3, 4, 5);

            assert false;
        } catch (InvalidParameterException ignored) {

        }
    }

    @Test
    public void testSquareRoot1() {
        try {
            OperationFactory.createOperation("sqrt");

            assert false;
        } catch (InvalidParameterException ignored) {

        }
    }

    @Test
    public void testSquareRoot2() {
        IOperation operation = OperationFactory.createOperation("sqrt", 4);
        assert operation.calculate() == 2;
    }

    @Test
    public void testSquareRoot3() {
        try {
            OperationFactory.createOperation("sqrt", 1, 2);

            assert false;
        } catch (InvalidParameterException ignored) {

        }
    }

    @Test
    public void testMinimum() {
        testMinimum1();
        testMinimum2();
        testMinimum3();
    }

    private void testMinimum1() {
        try {
            OperationFactory.createOperation("min");

            assert false;
        } catch (InvalidParameterException ignored) {

        }
    }

    private void testMinimum2() {
        IOperation operation = OperationFactory.createOperation("min", 1, 2);
        assert operation.calculate() == 1;
    }

    private void testMinimum3() {
        IOperation operation = OperationFactory.createOperation("min", 6, 2, 5, 4, 5);
        assert operation.calculate() == 2;
    }

    @Test
    public void testMaximum() {
        testMaximum1();
        testMaximum2();
        testMaximum3();
    }

    private void testMaximum1() {
        try {
            OperationFactory.createOperation("max");

            assert false;
        } catch (InvalidParameterException ignored) {

        }
    }

    private void testMaximum2() {
        IOperation operation = OperationFactory.createOperation("max", 1, 2);
        assert operation.calculate() == 2;
    }

    private void testMaximum3() {
        IOperation operation = OperationFactory.createOperation("max", 6, 2, 7, 4, 5);
        assert operation.calculate() == 7;
    }
}
