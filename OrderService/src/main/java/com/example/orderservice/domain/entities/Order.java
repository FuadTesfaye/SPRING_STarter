package com.example.orderservice.domain.entities;

import com.example.orderservice.domain.enums.OrderStatus;

public class Order {

    private final String orderId;
    private final Long productId;
    private final int quantity;
    private final double amount;
    private final OrderStatus status;
    private final String inventoryStatus;
    private final String paymentStatus;
    private final String shipmentId;
    private final String shipmentStatus;
    private final String notificationStatus;
    private final String message;

    public Order(
            String orderId,
            Long productId,
            int quantity,
            double amount,
            OrderStatus status,
            String inventoryStatus,
            String paymentStatus,
            String shipmentId,
            String shipmentStatus,
            String notificationStatus,
            String message
    ) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.amount = amount;
        this.status = status;
        this.inventoryStatus = inventoryStatus;
        this.paymentStatus = paymentStatus;
        this.shipmentId = shipmentId;
        this.shipmentStatus = shipmentStatus;
        this.notificationStatus = notificationStatus;
        this.message = message;
    }

    public String orderId() {
        return orderId;
    }

    public Long productId() {
        return productId;
    }

    public int quantity() {
        return quantity;
    }

    public double amount() {
        return amount;
    }

    public OrderStatus status() {
        return status;
    }

    public String inventoryStatus() {
        return inventoryStatus;
    }

    public String paymentStatus() {
        return paymentStatus;
    }

    public String shipmentId() {
        return shipmentId;
    }

    public String shipmentStatus() {
        return shipmentStatus;
    }

    public String notificationStatus() {
        return notificationStatus;
    }

    public String message() {
        return message;
    }

    public Order withOutcome(
            OrderStatus newStatus,
            String newInventoryStatus,
            String newPaymentStatus,
            String newShipmentId,
            String newShipmentStatus,
            String newNotificationStatus,
            String newMessage
    ) {
        return new Order(
                orderId,
                productId,
                quantity,
                amount,
                newStatus,
                newInventoryStatus,
                newPaymentStatus,
                newShipmentId,
                newShipmentStatus,
                newNotificationStatus,
                newMessage
        );
    }
}
