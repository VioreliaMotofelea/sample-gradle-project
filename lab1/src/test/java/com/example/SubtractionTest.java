package com.example;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SubtractionTest {

  @Test
  public void calculate_TwoPositiveNumbers_ReturnsDifference() {
    Subtraction subtraction = new Subtraction();

    // Act
    double actualResult = subtraction.calculate(2, 1);
    double expectedResult = 2 - 1;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_TwoNegativeNumbers_ReturnsDifference() {
    Subtraction subtraction = new Subtraction();

    // Act
    double actualResult = subtraction.calculate(-2, -1);
    double expectedResult = -2 - -1;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_PositiveAndNegativeNumbers_ReturnsCorrectNumber() {
    Subtraction subtraction = new Subtraction();

    // Act
    double actualResult = subtraction.calculate(2, -1);
    double expectedResult = 2 - -1;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_ZeroAndNumber_ReturnsSameNumber() {
    Subtraction subtraction = new Subtraction();

    // Act
    double actualResult = subtraction.calculate(0, 2);
    double expectedResult = 0 - 2;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_SameNumber_ReturnsZero() {
    Subtraction subtraction = new Subtraction();

    // Act
    double actualResult = subtraction.calculate(2, 2);
    double expectedResult = 2 - 2;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }


}
