package com.example.shipping.domain.model;

public class Shipping {

    private String username;
    private String status;

    public Shipping() {}

    public Shipping(String username, String status) {
        this.username = username;
        this.status = status;
    }

    public String getUsername() { return username; }
    public String getStatus() { return status; }
}
