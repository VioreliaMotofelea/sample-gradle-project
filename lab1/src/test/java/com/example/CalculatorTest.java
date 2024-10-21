package com.example;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CalculatorTest {

  @Test
  public void calculate_AdditionOfTwoPositiveNumbers_ReturnsPositiveNumber() {
    Calculator calculator = new Calculator();

    // Act
    double actualResult = calculator.calculate("+", 1, 2);
    double expectedResult = 1 + 2;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_AdditionOfTwoNegativeNumbers_ReturnsNegativeNumber() {
    Calculator calculator = new Calculator();

    // Act
    double actualResult = calculator.calculate("+", -1, -2);
    double expectedResult = -1 + -2;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_SubtractionOfTwoPositiveNumbers_ReturnsDifference() {
    Calculator calculator = new Calculator();

    // Act
    double actualResult = calculator.calculate("-", 2, 1);
    double expectedResult = 2 - 1;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_SubtractionOfTwoNegativeNumbers_ReturnsDifference() {
    Calculator calculator = new Calculator();

    // Act
    double actualResult = calculator.calculate("-", -2, -1);
    double expectedResult = -2 - -1;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_MultiplicationOfTwoPositiveNumbers_ReturnsPositiveNumber() {
    Calculator calculator = new Calculator();

    // Act
    double actualResult = calculator.calculate("*", 1, 2);
    double expectedResult = 1 * 2;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_MultiplicationOfTwoNegativeNumbers_ReturnsPositiveNumber() {
    Calculator calculator = new Calculator();

    // Act
    double actualResult = calculator.calculate("*", -1, -2);
    double expectedResult = -1 * -2;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));;
  }

  @Test
  public void calculate_DivisionOfTwoPositiveNumbers_ReturnsPositiveNumber() {
    Calculator calculator = new Calculator();

    // Act
    double actualResult = calculator.calculate("/", 1, 2);
    double expectedResult = 1 / 2.;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_DivisionOfTwoNegativeNumbers_ReturnsPositiveNumber() {
    Calculator calculator = new Calculator();

    // Act
    double actualResult = calculator.calculate("/", -1, -2);
    double expectedResult = -1 / -2.;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }
}
