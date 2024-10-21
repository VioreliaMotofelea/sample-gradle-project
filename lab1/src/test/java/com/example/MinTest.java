package com.example;

import org.junit.jupiter.api.Test;

public class MinTest {

  @Test
  public void calculate_TwoPositiveNumbers_ReturnsPositiveNumber() {
    Min min = new Min();

    // Act
    double actualResult = min.calculate(1, 2);
    double expectedResult = 1;
    // Assert
    assert(actualResult == expectedResult);
  }

  @Test
  public void calculate_TwoNegativeNumbers_ReturnsNegativeNumber() {
    Min min = new Min();

    // Act
    double actualResult = min.calculate(-1, -2);
    double expectedResult = -2;
    // Assert
    assert(actualResult == expectedResult);
  }

  @Test
  public void calculate_PositiveAndNegativeNumbers_ReturnsNegativeNumber() {
    Min min = new Min();

    // Act
    double actualResult = min.calculate(1, -2);
    double expectedResult = -2;
    // Assert
    assert(actualResult == expectedResult);
  }

  @Test
  public void calculate_NegativeAndPositiveNumbers_ReturnsNegativeNumber() {
    Min min = new Min();

    // Act
    double actualResult = min.calculate(-1, 2);
    double expectedResult = -1;
    // Assert
    assert(actualResult == expectedResult);
  }

  @Test
  public void calculate_ZeroAndPositiveNumber_ReturnsZero() {
    Min min = new Min();

    // Act
    double actualResult = min.calculate(0, 2);
    double expectedResult = 0;
    // Assert
    assert(actualResult == expectedResult);
  }

  @Test
  public void calculate_ZeroAndNegativeNumber_ReturnsNegativeNumber(){
    Min min = new Min();

    // Act
    double actualResult = min.calculate(0, -2);
    double expectedResult = -2;
    // Assert
    assert(actualResult == expectedResult);
  }
}
