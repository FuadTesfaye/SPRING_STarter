package com.school.payment.application.service;

import com.school.payment.application.port.EventPublisher;
import com.school.payment.application.port.PaymentRepository;
import com.school.payment.domain.entity.Payment;
import com.school.payment.domain.event.PaymentCompletedEvent;
import com.school.payment.domain.event.PaymentFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final EventPublisher eventPublisher;
    private final Random random = new Random();

    public void processPayment(Long orderId, String studentId, BigDecimal amount) {
        log.info("[PAYMENT] Processing payment for order: {}", orderId);

        Payment payment = new Payment(orderId, studentId, amount);

        // Simulate payment: 80% success rate
        boolean success = random.nextInt(10) < 8;

        if (success) {
            payment.setStatus("SUCCESS");
            payment.setReference("PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            Payment saved = paymentRepository.save(payment);

            PaymentCompletedEvent event = new PaymentCompletedEvent(
                orderId, saved.getId(), studentId, amount, saved.getReference()
            );
            eventPublisher.publish("payment.completed", event);
            log.info("[PAYMENT] Payment SUCCESS for order: {} ref: {}", orderId, saved.getReference());
        } else {
            payment.setStatus("FAILED");
            payment.setReference("N/A");
            paymentRepository.save(payment);

            PaymentFailedEvent event = new PaymentFailedEvent(
                orderId, studentId, amount, "Insufficient funds or bank declined"
            );
            eventPublisher.publish("payment.failed", event);
            log.warn("[PAYMENT] Payment FAILED for order: {}", orderId);
        }
    }
}
