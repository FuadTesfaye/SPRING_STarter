package com.example.inventory.domain.model;

public class Inventory {

    private String username;
    private String status;

    public Inventory() {}

    public Inventory(String username, String status) {
        this.username = username;
        this.status = status;
    }

    public String getUsername() { return username; }
    public String getStatus() { return status; }
}
