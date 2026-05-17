package com.example.paymentservice.domain.services;

import com.example.paymentservice.domain.entities.Payment;
import com.example.paymentservice.domain.enums.PaymentStatus;
import java.util.UUID;

public class PaymentDomainService {

    public Payment createPayment(String orderId, Long productId, int quantity, double amount) {
        return new Payment(UUID.randomUUID().toString(), orderId, productId, quantity, amount, PaymentStatus.PAYMENT_CONFIRMED);
    }
}
