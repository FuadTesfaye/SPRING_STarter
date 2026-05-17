package com.example.task.order.model;

public class Order {
    private String orderId;
    private String customerId;
    private String productId;
    private int quantity;
    private double totalAmount;
    private String status;

    public Order(String orderId, String customerId, String productId, int quantity, double totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.status = "PENDING";
    }

    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }

    public void markAsPaid() { this.status = "PAID"; }
    public void markAsFailed() { this.status = "FAILED"; }
}
