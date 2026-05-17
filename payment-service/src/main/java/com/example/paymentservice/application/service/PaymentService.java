package com.example.paymentservice.application.service;

import com.example.paymentservice.application.dto.OrderCreatedEvent;
import com.example.paymentservice.domain.model.Payment;
import com.example.paymentservice.domain.repository.PaymentRepository;
import com.example.paymentservice.infrastructure.messaging.PaymentEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher eventPublisher;

    public PaymentService(PaymentRepository paymentRepository,
                          PaymentEventPublisher eventPublisher) {
        this.paymentRepository = paymentRepository;
        this.eventPublisher = eventPublisher;
    }

    public void processPayment(OrderCreatedEvent event) {

        // ✅ IDEMPOTENCY → PREVENT DUPLICATE + LOOP
        if (paymentRepository.existsByOrderId(event.getOrderId())) {
            System.out.println("⚠️ Already processed order: " + event.getOrderId());
            return;
        }

        Payment payment = new Payment(
                event.getOrderId(),
                event.getUserId(),
                event.getTotalAmount() != null ? event.getTotalAmount() : 0.0
        );

        boolean isSuccess = Math.random() > 0.2;

        if (isSuccess) {
            payment.setStatus("COMPLETED");
            paymentRepository.save(payment);
            eventPublisher.publishPaymentCompleted(payment);
        } else {
            payment.setStatus("FAILED");
            paymentRepository.save(payment);
            eventPublisher.publishPaymentFailed(payment);
        }
    }
}