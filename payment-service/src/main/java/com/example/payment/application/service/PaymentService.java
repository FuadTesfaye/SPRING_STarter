package com.example.payment.application.service;

import com.example.payment.domain.model.Payment;
import com.example.payment.domain.port.PaymentRepositoryPort;
import com.example.payment.infrastructure.messaging.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepositoryPort repository;
    private final RabbitTemplate rabbitTemplate;

    public PaymentService(PaymentRepositoryPort repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void processPayment(String username) {
        Payment payment = new Payment(username, "SUCCESS");
        repository.save(payment);
        System.out.println("Payment processed for: " + username);
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.PAYMENT_PROCESSED_EXCHANGE,
            RabbitMQConfig.PAYMENT_PROCESSED_ROUTING_KEY,
            username
        );
    }
}
