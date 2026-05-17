package com.microservices.order.domain.exception;

public class OrderNotFoundException extends OrderDomainException {
    public OrderNotFoundException(String orderId) {
        super("Order not found: " + orderId);
    }
}
