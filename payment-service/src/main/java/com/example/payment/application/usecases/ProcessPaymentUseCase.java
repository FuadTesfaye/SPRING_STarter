package com.example.payment.application.usecases;

import com.example.payment.application.ports.PaymentEventPublisher;
import com.example.payment.domain.model.Payment;
import com.example.payment.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessPaymentUseCase {
    private final PaymentRepository paymentRepository;
    private final PaymentEventPublisher eventPublisher;

    public void processOrderPayment(Long orderId, Double amount) {
        System.out.println("Processing payment for Order: " + orderId + " Amount: " + amount);

        String status = (amount > 5000) ? "FAILED" : "SUCCESS";

        Payment payment = new Payment(null, orderId, amount, status);
        paymentRepository.save(payment);

        if ("SUCCESS".equals(status)) {
            eventPublisher.publishPaymentCompleted(orderId);
        } else {
            eventPublisher.publishPaymentFailed(orderId, "Insufficient funds (Simulated)");
        }
    }
}