package com.example.payment.application.service;

import com.example.payment.domain.event.OrderCreatedEvent;
import com.example.payment.domain.event.PaymentEvent;
import com.example.payment.domain.model.Payment;
import com.example.payment.domain.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Random;

@Service
@Transactional
public class PaymentService {
    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentRepository paymentRepository;
    private final RabbitTemplate rabbitTemplate;
    private final Random random = new Random();

    public PaymentService(PaymentRepository paymentRepository, RabbitTemplate rabbitTemplate) {
        this.paymentRepository = paymentRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void processPayment(OrderCreatedEvent event) {
        log.info("Processing payment for order: {}", event.getOrderId());
        
        Payment payment = new Payment(event.getOrderId(), event.getUserId(), event.getTotalAmount());
        payment = paymentRepository.save(payment);

        // Mock payment: 80% success rate
        if (random.nextDouble() < 0.8) {
            payment.setStatus("COMPLETED");
            paymentRepository.save(payment);
            
            PaymentEvent paymentEvent = new PaymentEvent(
                event.getOrderId(), event.getUserId(), payment.getId(),
                payment.getTransactionId(), payment.getAmount(), "PaymentCompleted"
            );
            rabbitTemplate.convertAndSend("app.exchange", "payment.completed", paymentEvent);
            log.info("Payment completed for order: {}", event.getOrderId());
        } else {
            payment.setStatus("FAILED");
            paymentRepository.save(payment);
            
            PaymentEvent paymentEvent = new PaymentEvent(
                event.getOrderId(), event.getUserId(), payment.getId(),
                payment.getTransactionId(), payment.getAmount(), "PaymentFailed"
            );
            rabbitTemplate.convertAndSend("app.exchange", "payment.failed", paymentEvent);
            log.info("Payment failed for order: {}", event.getOrderId());
        }
    }
}