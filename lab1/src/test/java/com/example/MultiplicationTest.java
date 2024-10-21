package com.example;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MultiplicationTest {

  @Test
  public void calculate_TwoPositiveNumbers_ReturnsPositiveNumber() {
    Multiplication multiplication = new Multiplication();

    // Act
    double actualResult = multiplication.calculate(1, 2);
    double expectedResult = 1 * 2;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_TwoNegativeNumbers_ReturnsPositiveNumber() {
    Multiplication multiplication = new Multiplication();

    // Act
    double actualResult = multiplication.calculate(-1, -2);
    double expectedResult = -1 * -2;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_PositiveAndNegativeNumbers_ReturnsNegativeNumber() {
    Multiplication multiplication = new Multiplication();

    // Act
    double actualResult = multiplication.calculate(1, -2);
    double expectedResult = 1 * -2;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_NegativeAndPositiveNumbers_ReturnsNegativeNumber() {
    Multiplication multiplication = new Multiplication();

    // Act
    double actualResult = multiplication.calculate(-1, 2);
    double expectedResult = -1 * 2;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_ZeroAndPositiveNumber_ReturnsZero() {
    Multiplication multiplication = new Multiplication();

    // Act
    double actualResult = multiplication.calculate(0, 2);
    double expectedResult = 0 * 2;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_PositiveNumberAndZero_ReturnsZero() {
    Multiplication multiplication = new Multiplication();

    // Act
    double actualResult = multiplication.calculate(2, 0);
    double expectedResult = 2 * 0;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_ZeroAndZero_ReturnsZero() {
    Multiplication multiplication = new Multiplication();

    // Act
    double actualResult = multiplication.calculate(0, 0);
    double expectedResult = 0 * 0;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }
}
