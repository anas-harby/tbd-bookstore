package com.tbdbookstore.core.pojo;

public class Order {

    private int id;
    private String ISBN;
    private int quantity;

    public Order(String ISBN, int quantity) {
        this.ISBN = ISBN;
        this.quantity = quantity;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getISBN() {
        return ISBN;
    }

    public int getQuantity() {
        return quantity;
    }
}
