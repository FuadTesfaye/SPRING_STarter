package com.microservices.shippingservice.application.event;

public class InventoryEvent {

    private String username;

    public InventoryEvent() {
    }

    public InventoryEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}