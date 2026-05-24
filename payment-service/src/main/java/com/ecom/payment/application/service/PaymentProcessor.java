package com.ecom.payment.application.service;

import com.ecom.payment.application.dto.OrderCreatedEvent;
import com.ecom.payment.application.dto.PaymentProcessedEvent;
import com.ecom.payment.application.port.EventPublisher;
import com.ecom.payment.application.port.PaymentGateway;
import com.ecom.payment.domain.model.Payment;
import com.ecom.payment.domain.repository.PaymentRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentProcessor {
    private final PaymentRepository paymentRepository;
    private final EventPublisher eventPublisher;
    private final PaymentGateway paymentGateway;

    public PaymentProcessor(PaymentRepository paymentRepository, 
                            EventPublisher eventPublisher, 
                            PaymentGateway paymentGateway) {
        this.paymentRepository = paymentRepository;
        this.eventPublisher = eventPublisher;
        this.paymentGateway = paymentGateway;
    }

    public void processPayment(OrderCreatedEvent event) {
        boolean success = paymentGateway.process(event.totalAmount());
        String status = success ? "COMPLETED" : "FAILED";

        Payment payment = new Payment(
                UUID.randomUUID(),
                event.orderId(),
                event.totalAmount(),
                status,
                LocalDateTime.now()
        );

        paymentRepository.save(payment);

        eventPublisher.publishPaymentProcessed(new PaymentProcessedEvent(
                event.orderId(),
                status
        ));
    }
}
