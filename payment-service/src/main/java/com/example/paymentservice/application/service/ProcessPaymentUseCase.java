package com.example.paymentservice.application.service;
import com.example.paymentservice.application.dto.OrderCreatedMessage;
import com.example.paymentservice.application.port.*;
import com.example.paymentservice.domain.event.PaymentCompletedEvent;
import com.example.paymentservice.domain.event.PaymentFailedEvent;
import com.example.paymentservice.domain.model.Payment;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class ProcessPaymentUseCase {
    private static final Logger log = LoggerFactory.getLogger(ProcessPaymentUseCase.class);
    private final PaymentRepositoryPort repo;
    private final EventPublisherPort publisher;
    public ProcessPaymentUseCase(PaymentRepositoryPort r, EventPublisherPort p) { repo=r; publisher=p; }

    public void handle(OrderCreatedMessage m) {
        // Mock: fail if amount > 10000
        boolean ok = m.amount != null && m.amount.compareTo(new BigDecimal("10000")) <= 0;
        Payment p = new Payment(UUID.randomUUID(), m.orderId, m.amount,
            ok ? Payment.Status.COMPLETED : Payment.Status.FAILED, Instant.now());
        repo.save(p);
        if (ok) {
            log.info("Payment OK for order {}", m.orderId);
            publisher.publish("payment.completed",
                new PaymentCompletedEvent(p.getId(), m.orderId, m.amount, Instant.now()));
        } else {
            log.warn("Payment FAILED for order {}", m.orderId);
            publisher.publish("payment.failed",
                new PaymentFailedEvent(m.orderId, "amount exceeds limit", Instant.now()));
        }
    }
}
