package com.example;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SqrtTest {

  @Test
  public void calculate_PositiveNumber_ReturnsPositiveNumber() {
    Sqrt sqrt = new Sqrt();

    // Act
    double actualResult = sqrt.calculate(4);
    double expectedResult = 2;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }

  @Test
  public void calculate_NegativeNumber_ThrowsIllegalArgumentException() {
    Sqrt sqrt = new Sqrt();

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> sqrt.calculate(-4));
  }

  @Test
  public void calculate_Zero_ReturnsZero() {
    Sqrt sqrt = new Sqrt();

    // Act
    double actualResult = sqrt.calculate(0);
    double expectedResult = 0;
    // Assert
    assertThat(actualResult, equalTo(expectedResult));
  }
}
