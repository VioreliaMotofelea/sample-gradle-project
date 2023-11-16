package org.example.entity;

public class Order implements Comparable<Order> {
    private Integer id;
    private Integer price;
    private Integer quantity;

    public Order(Integer id, Integer price, Integer quantity) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

    public boolean equals(Order other) {
        return this.hashCode() == other.hashCode();
    }

    public int hashCode() {
        return this.id.hashCode() + this.price.hashCode() + this.quantity.hashCode();
    }

    public String toString() {
        return "" + this.id + ", " + this.price + ", " + this.quantity;
    }

    public int compareTo(Order other) {
        return this.id.compareTo(other.id);
    }

    public int compareByPriceTo(Order other) {
        return this.price.compareTo(other.price);
    }
}
