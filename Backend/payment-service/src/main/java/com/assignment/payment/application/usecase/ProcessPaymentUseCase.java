package com.assignment.payment.application.usecase;

import com.assignment.payment.application.dto.OrderCreatedEvent;
import com.assignment.payment.domain.event.PaymentCompleted;
import com.assignment.payment.domain.event.PaymentFailed;
import com.assignment.payment.domain.model.Payment;
import com.assignment.payment.domain.repository.PaymentRepository;
import com.assignment.payment.infrastructure.messaging.EventPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Service
public class ProcessPaymentUseCase {

    private static final Logger log = LoggerFactory.getLogger(ProcessPaymentUseCase.class);

    private final PaymentRepository repository;
    private final EventPublisher publisher;
    private final ObjectMapper mapper;

    public ProcessPaymentUseCase(PaymentRepository repository, EventPublisher publisher, ObjectMapper mapper) {
        this.repository = repository;
        this.publisher = publisher;
        this.mapper = mapper;
    }

    public void execute(Map<String, Object> raw) {
        OrderCreatedEvent event = mapper.convertValue(raw, OrderCreatedEvent.class);

        UUID orderId = UUID.fromString(event.orderId());
        UUID userId  = UUID.fromString(event.userId());
        BigDecimal amount = event.price().multiply(BigDecimal.valueOf(event.quantity()));

        Payment payment = Payment.create(orderId, userId, amount);
        Payment saved = repository.save(payment);

        log.info("[PAYMENT] Processing payment for order={} amount={}", orderId, amount);

        // Mock logic: orders with quantity > 10 simulate insufficient funds
        boolean success = event.quantity() <= 10;

        if (success) {
            saved.setStatus("COMPLETED");
            repository.save(saved);
            publisher.publish("payment.completed",
                PaymentCompleted.of(saved.getId(), saved.getOrderId(), saved.getUserId(), saved.getAmount()));
            log.info("[PAYMENT] COMPLETED — paymentId={} orderId={}", saved.getId(), orderId);
        } else {
            saved.setStatus("FAILED");
            repository.save(saved);
            publisher.publish("payment.failed",
                PaymentFailed.of(saved.getId(), saved.getOrderId(), saved.getUserId(), "Quantity exceeds limit"));
            log.warn("[PAYMENT] FAILED — paymentId={} orderId={}", saved.getId(), orderId);
        }
    }
}
