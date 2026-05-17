package com.example.notification.application.event;

public class ShippingEvent {
    private String username;

    public ShippingEvent() {}
    public ShippingEvent(String username) { this.username = username; }
    public String getUsername() { return username; }
}
