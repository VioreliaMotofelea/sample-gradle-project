package com.example;

import org.junit.jupiter.api.Test;

public class MaxTest {

  @Test
  public void calculate_TwoPositiveNumbers_ReturnsPositiveNumber() {
    Max max = new Max();

    // Act
    double actualResult = max.calculate(1, 2);
    double expectedResult = 2;
    // Assert
    assert(actualResult == expectedResult);
  }

  @Test
  public void calculate_TwoNegativeNumbers_ReturnsNegativeNumber() {
    Max max = new Max();

    // Act
    double actualResult = max.calculate(-1, -2);
    double expectedResult = -1;
    // Assert
    assert(actualResult == expectedResult);
  }

  @Test
  public void calculate_PositiveAndNegativeNumbers_ReturnsPositiveNumber() {
    Max max = new Max();

    // Act
    double actualResult = max.calculate(1, -2);
    double expectedResult = 1;
    // Assert
    assert(actualResult == expectedResult);
  }

  @Test
  public void calculate_NegativeAndPositiveNumbers_ReturnsPositiveNumber() {
    Max max = new Max();

    // Act
    double actualResult = max.calculate(-1, 2);
    double expectedResult = 2;
    // Assert
    assert(actualResult == expectedResult);
  }

  @Test
  public void calculate_ZeroAndPositiveNumber_ReturnsPositiveNumber() {
    Max max = new Max();

    // Act
    double actualResult = max.calculate(0, 2);
    double expectedResult = 2;
    // Assert
    assert(actualResult == expectedResult);
  }

  @Test
  public void calculate_ZeroAndNegativeNumber_ReturnsZero(){
    Max max = new Max();

    // Act
    double actualResult = max.calculate(0, -2);
    double expectedResult = 0;
    // Assert
    assert(actualResult == expectedResult);
  }

  @Test
  public void calculate_SameNumber_ReturnsSameNumber() {
    Max max = new Max();

    // Act
    double actualResult = max.calculate(2, 2);
    double expectedResult = 2;
    // Assert
    assert(actualResult == expectedResult);
  }

  @Test
  public void calculate_SameNegativeNumber_ReturnsSameNumber() {
    Max max = new Max();

    // Act
    double actualResult = max.calculate(-2, -2);
    double expectedResult = -2;
    // Assert
    assert(actualResult == expectedResult);
  }

}
