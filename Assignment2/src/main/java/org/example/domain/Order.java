package org.example.domain;

public class Order implements Comparable<Order>{
    private int id;
    private int price;
    private int qty;

    public Order(int id, int price, int qty) {
        this.id = id;
        this.price = price;
        this.qty = qty;
    }

    @Override
    public int hashCode() {
        return ((Integer) this.id).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Order order)) return false;
        return this.id == order.id;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", price=" + price +
                ", qty=" + qty +
                '}';
    }

    @Override
    public int compareTo(Order order) {
        if (!this.equals(order)) return this.id - order.id;
        return 0;
    }
}
