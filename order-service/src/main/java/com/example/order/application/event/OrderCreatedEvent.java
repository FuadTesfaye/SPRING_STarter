package com.microservices.orderservice.application.event;

public class OrderCreatedEvent {

    private String username;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}