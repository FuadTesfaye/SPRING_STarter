package com.microservices.paymentservice.application.event;

public class PaymentEvent {

    private String username;

    public PaymentEvent() {
    }

    public PaymentEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}