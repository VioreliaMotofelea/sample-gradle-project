package com.example;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DivisionTest {

  @Test
  public void calculate_TwoPositiveNumbers_ReturnsPositiveNumber() {
    Division division = new Division();

    // Act
    double actualResult = division.calculate(1, 2);
    double expectedResult = 1 / 2.;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_TwoNegativeNumbers_ReturnsPositiveNumber() {
    Division division = new Division();

    // Act
    double actualResult = division.calculate(-1, -2);
    double expectedResult = -1 / -2.;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_PositiveAndNegativeNumbers_ReturnsNegativeNumber() {
    Division division = new Division();

    // Act
    double actualResult = division.calculate(1, -2);
    double expectedResult = 1 / -2.;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_NegativeAndPositiveNumbers_ReturnsNegativeNumber() {
    Division division = new Division();

    // Act
    double actualResult = division.calculate(-1, 2);
    double expectedResult = -1 / 2.;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_ZeroAndPositiveNumber_ReturnsZero() {
    Division division = new Division();

    // Act
    double actualResult = division.calculate(0, 2);
    double expectedResult = 0 / 2;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_DivisionByOne_ReturnsSameNumber() {
    Division division = new Division();

    // Act
    double actualResult = division.calculate(2, 1);
    double expectedResult = 2 / 1;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_DivisionByZero_ReturnsException() {
    Division division = new Division();

    assertThrows(ArithmeticException.class, () -> {
      division.calculate(2, 0);
    });
  }
}
