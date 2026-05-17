package com.eventdriven.paymentservice.application.service;

import com.eventdriven.paymentservice.application.dto.OrderCreatedEvent;
import com.eventdriven.paymentservice.domain.model.Payment;
import com.eventdriven.paymentservice.domain.repository.PaymentRepository;
import com.eventdriven.paymentservice.infrastructure.messaging.RabbitMQSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentApplicationService {
    
    private static final Logger log = LoggerFactory.getLogger(PaymentApplicationService.class);
    private final PaymentRepository paymentRepository;
    private final RabbitMQSender rabbitMQSender;
    
    public PaymentApplicationService(PaymentRepository paymentRepository, RabbitMQSender rabbitMQSender) {
        this.paymentRepository = paymentRepository;
        this.rabbitMQSender = rabbitMQSender;
    }
    
    public void processPayment(OrderCreatedEvent event) {
        log.info("Processing payment for order: {}", event.getOrderId());
        
        // Mock payment processing - 90% success rate
        boolean paymentSuccess = Math.random() < 0.9;
        
        if (paymentSuccess) {
            Payment payment = new Payment();
            payment.setOrderId(event.getOrderId());
            payment.setAmount(event.getPrice().multiply(new BigDecimal(event.getQuantity())));
            payment.setStatus("COMPLETED");
            payment.setCreatedAt(LocalDateTime.now());
            paymentRepository.save(payment);
            
            rabbitMQSender.sendPaymentCompletedEvent(event.getOrderId(), payment.getId(), payment.getAmount());
            log.info("Payment completed for order: {}", event.getOrderId());
        } else {
            rabbitMQSender.sendPaymentFailedEvent(event.getOrderId(), "Insufficient funds");
            log.info("Payment failed for order: {}", event.getOrderId());
        }
    }
}