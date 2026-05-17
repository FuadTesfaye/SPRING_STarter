package com.example.task.payment.model;

public class Transaction {
    private String transactionId;
    private String orderId;
    private double amount;
    private String paymentStatus;

    public Transaction(String transactionId, String orderId, double amount) {
        this.transactionId = transactionId;
        this.orderId = orderId;
        this.amount = amount;
        this.paymentStatus = "PROCESSING";
    }

    public String getTransactionId() { return transactionId; }
    public String getOrderId() { return orderId; }
    public double getAmount() { return amount; }
    public String getPaymentStatus() { return paymentStatus; }

    public void approve() { this.paymentStatus = "SUCCESS"; }
    public void reject() { this.paymentStatus = "REJECTED"; }
}
