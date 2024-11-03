package com.example.model;

public class Order implements Comparable<Order> {
  private final int id;
  private final int price;
  private final int quantity;

  public Order(int id, int price, int quantity) {
    this.id = id;
    this.price = price;
    this.quantity = quantity;
  }

  @Override
  public boolean equals(Object other) {
    if(this == other) return true;
    if(!(other instanceof Order)) return false;
    Order order = (Order) other;
    return id == order.id;
  }

  @Override
  public int hashCode() {
    int result = Integer.hashCode(id);
    result = 31 * result + Integer.hashCode(price);
    result = 31 * result + Integer.hashCode(quantity);

    return result;
  }

  @Override
  public String toString() {
    return "Order " + id + ": price=" + price + ", quantity=" + quantity;
  }

  @Override
  public int compareTo(Order other) {
    return Integer.compare(this.id, other.id);
  }
}
