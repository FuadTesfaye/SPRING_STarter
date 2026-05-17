package com.example.payment.domain.model;

public class Payment {

    private String username;
    private String status;

    public Payment() {}

    public Payment(String username, String status) {
        this.username = username;
        this.status = status;
    }

    public String getUsername() { return username; }
    public String getStatus() { return status; }
}
