package com.viorelia.repo;

import java.util.Objects;

public class Order implements Comparable<Order> {
    private final int id;
    private final int price;
    private final int quantity;

    public Order(int id, int price, int quantity) {
        this.id = id; this.price = price; this.quantity = quantity;
    }

    public int id() { return id; }
    public int price() { return price; }
    public int quantity() { return quantity; }

    @Override public int compareTo(Order o) {
        int c = Integer.compare(id, o.id);
        if (c != 0) return c;
        c = Integer.compare(price, o.price);
        if (c != 0) return c;
        return Integer.compare(quantity, o.quantity);
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order that = (Order) o;
        return id == that.id && price == that.price && quantity == that.quantity;
    }

    @Override public int hashCode() { return Objects.hash(id, price, quantity); }

    @Override public String toString() {
        return "Order{id=" + id + ", price=" + price + ", quantity=" + quantity + '}';
    }
}
