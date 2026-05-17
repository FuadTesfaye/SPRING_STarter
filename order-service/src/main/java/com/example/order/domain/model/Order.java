package com.example.order.domain.model;

public class Order {

    private String username;
    private String status;

    public Order() {}

    public Order(String username, String status) {
        this.username = username;
        this.status = status;
    }

    public String getUsername() { return username; }
    public String getStatus() { return status; }
}
