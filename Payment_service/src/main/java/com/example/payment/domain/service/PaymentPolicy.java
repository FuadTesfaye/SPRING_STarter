package com.example.payment.domain.service;

/**
 * Domain service — mock payment decision rules (pure Java).
 */
public class PaymentPolicy {

    public enum Outcome {
        COMPLETED,
        FAILED
    }

    public record Decision(Outcome outcome, String failureReason, double amount) {
    }

    public Decision evaluate(String productId, double totalAmount) {
        if (totalAmount <= 0) {
            return new Decision(Outcome.FAILED, "Invalid amount", totalAmount);
        }
        String pid = productId == null ? "" : productId;
        if (pid.contains("PAYMENT_FAIL")) {
            return new Decision(Outcome.FAILED, "Mock decline for PAYMENT_FAIL sku", totalAmount);
        }
        return new Decision(Outcome.COMPLETED, null, totalAmount);
    }
}
