package com.ecommerce.payment.application.service;

import com.ecommerce.payment.application.ports.EventPublisher;
import com.ecommerce.shared.messaging.event.PaymentCompletedEvent;
import com.ecommerce.shared.messaging.event.PaymentFailedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentApplicationService {

    private final EventPublisher eventPublisher;

    public void processPayment(String orderId, String userId, java.math.BigDecimal amount) {
        // Mock payment logic: succeed 90% of the time
        boolean success = Math.random() < 0.9;

        if (success) {
            String paymentId = UUID.randomUUID().toString();
            eventPublisher.publish(new PaymentCompletedEvent(paymentId, orderId));
        } else {
            eventPublisher.publish(new PaymentFailedEvent(orderId, "Insufficient funds"));
        }
    }
}
