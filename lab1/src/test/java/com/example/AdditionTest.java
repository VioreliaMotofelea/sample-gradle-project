package com.example;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;

public class AdditionTest {

  @Test
  public void calculate_TwoPositiveNumbers_ReturnsPositiveNumber() {
    Addition addition = new Addition();

    // Act
    double actualResult = addition.calculate(1, 2);
    double expectedResult = 1 + 2;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_TwoNegativeNumbers_ReturnsNegativeNumber() {
    Addition addition = new Addition();

    // Act
    double actualResult = addition.calculate(-1, -2);
    double expectedResult = -1 + -2;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_PositiveAndNegativeNumbers_ReturnsCorrectNumber() {
    Addition addition = new Addition();

    // Act
    double actualResult = addition.calculate(1, -2);
    double expectedResult = 1 + -2;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_NegativeAndPositiveNumbers_ReturnsCorrectNumber() {
    Addition addition = new Addition();

    // Act
    double actualResult = addition.calculate(-1, 2);
    double expectedResult = -1 + 2;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_ZeroAndPositiveNumber_ReturnsPositiveNumber() {
    Addition addition = new Addition();

    // Act
    double actualResult = addition.calculate(0, 2);
    double expectedResult = 0 + 2;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_PositiveNumberAndZero_ReturnsPositiveNumber() {
    Addition addition = new Addition();

    // Act
    double actualResult = addition.calculate(2, 0);
    double expectedResult = 2 + 0;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_ZeroAndNegativeNumber_ReturnsNegativeNumber() {
    Addition addition = new Addition();

    // Act
    double actualResult = addition.calculate(0, -2);
    double expectedResult = 0 + -2;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_NegativeNumberAndZero_ReturnsNegativeNumber() {
    Addition addition = new Addition();

    // Act
    double actualResult = addition.calculate(-2, 0);
    double expectedResult = -2 + 0;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

}
