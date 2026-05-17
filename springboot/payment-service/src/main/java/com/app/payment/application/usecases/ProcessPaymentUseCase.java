package com.app.payment.application.usecases;

import com.app.payment.application.ports.PaymentEventPublisher;
import com.app.payment.domain.Payment;
import com.app.payment.domain.PaymentRepository;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

public class ProcessPaymentUseCase {
    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher eventPublisher;
    private final Random random = new Random();

    public ProcessPaymentUseCase(PaymentRepository paymentRepository, PaymentEventPublisher eventPublisher) {
        this.paymentRepository = paymentRepository;
        this.eventPublisher = eventPublisher;
    }

    public void execute(UUID orderId, BigDecimal amount) {
        // Mock payment logic
        boolean success = random.nextBoolean();
        String status = success ? "COMPLETED" : "FAILED";
        
        Payment payment = new Payment(UUID.randomUUID(), orderId, amount, status);
        paymentRepository.save(payment);
        
        if (success) {
            eventPublisher.publishPaymentCompleted(payment);
        } else {
            eventPublisher.publishPaymentFailed(payment);
        }
    }
}
