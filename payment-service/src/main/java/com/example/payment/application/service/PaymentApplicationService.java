package com.example.payment.application.service;

import com.example.events.OrderCreatedEvent;
import com.example.payment.application.port.out.PaymentEventPublisher;
import com.example.payment.application.port.out.PaymentRepository;
import com.example.payment.domain.model.Payment;
import com.example.payment.domain.model.PaymentStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class PaymentApplicationService {

    private static final BigDecimal FAILURE_THRESHOLD = new BigDecimal("500.00");

    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher paymentEventPublisher;

    public PaymentApplicationService(
            PaymentRepository paymentRepository,
            PaymentEventPublisher paymentEventPublisher
    ) {
        this.paymentRepository = paymentRepository;
        this.paymentEventPublisher = paymentEventPublisher;
    }

    @Transactional
    public void processPayment(OrderCreatedEvent event) {
        BigDecimal amount = estimateAmount(event.quantity());
        Payment payment = shouldFail(amount)
                ? createFailedPayment(event.orderId(), amount)
                : createCompletedPayment(event.orderId(), amount);

        Payment savedPayment = paymentRepository.save(payment);

        publishPaymentEventAsync(savedPayment);
    }

    @Async
    private void publishPaymentEventAsync(Payment payment) {
        try {
            if (payment.getStatus() == PaymentStatus.COMPLETED) {
                paymentEventPublisher.publishPaymentCompleted(payment);
                System.out.println("✅ Payment completed event published");
                return;
            }
            paymentEventPublisher.publishPaymentFailed(payment);
            System.out.println("❌ Payment failed event published");
        } catch (Exception e) {
            System.err.println("⚠️ Failed to publish payment event: " + e.getMessage());
        }
    }

    private Payment createCompletedPayment(Long orderId, BigDecimal amount) {
        return new Payment(
                null,
                orderId,
                amount,
                PaymentStatus.COMPLETED,
                "PAY-" + orderId + "-" + Instant.now().toEpochMilli(),
                null,
                Instant.now()
        );
    }

    private Payment createFailedPayment(Long orderId, BigDecimal amount) {
        return new Payment(
                null,
                orderId,
                amount,
                PaymentStatus.FAILED,
                null,
                "Mock payment rejected because total exceeds " + FAILURE_THRESHOLD,
                Instant.now()
        );
    }

    private boolean shouldFail(BigDecimal amount) {
        return amount.compareTo(FAILURE_THRESHOLD) > 0;
    }

    private BigDecimal estimateAmount(int quantity) {
        return BigDecimal.valueOf(quantity).multiply(new BigDecimal("120.00"));
    }
}
