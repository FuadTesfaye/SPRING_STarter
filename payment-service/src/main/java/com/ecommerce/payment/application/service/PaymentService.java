package com.ecommerce.payment.application.service;

import com.ecommerce.payment.application.port.EventPublisher;
import com.ecommerce.payment.domain.event.*;
import com.ecommerce.payment.domain.model.Payment;
import com.ecommerce.payment.domain.model.PaymentStatus;
import com.ecommerce.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final EventPublisher eventPublisher;

    public void processPayment(OrderCreatedEvent event) {
        log.info("[PAYMENT] Processing payment for order: {}", event.getOrderId());

        Payment payment = Payment.builder()
                .orderId(event.getOrderId())
                .amount(event.getAmount())
                .status(PaymentStatus.PENDING)
                .processedAt(LocalDateTime.now())
                .build();

        boolean success = payment.process();
        Payment saved = paymentRepository.save(payment);

        if (success) {
            log.info("[PAYMENT] Payment completed for order: {}", event.getOrderId());
            eventPublisher.publish("payment.completed", PaymentCompletedEvent.builder()
                    .paymentId(saved.getId())
                    .orderId(saved.getOrderId())
                    .amount(saved.getAmount())
                    .timestamp(LocalDateTime.now())
                    .build());
        } else {
            log.warn("[PAYMENT] Payment FAILED for order: {} - {}", event.getOrderId(), saved.getFailureReason());
            eventPublisher.publish("payment.failed", PaymentFailedEvent.builder()
                    .paymentId(saved.getId())
                    .orderId(saved.getOrderId())
                    .reason(saved.getFailureReason())
                    .timestamp(LocalDateTime.now())
                    .build());
        }
    }

    public Optional<Payment> findByOrderId(String orderId) { return paymentRepository.findByOrderId(orderId); }
    public List<Payment> findAll() { return paymentRepository.findAll(); }
}
