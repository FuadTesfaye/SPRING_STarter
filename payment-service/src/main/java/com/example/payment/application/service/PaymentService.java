package com.microservices.paymentservice.application.service;

import com.microservices.paymentservice.application.event.PaymentEvent;
import com.microservices.paymentservice.domain.model.Payment;
import com.microservices.paymentservice.domain.port.PaymentRepositoryPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepositoryPort repository;
    private final RabbitTemplate rabbitTemplate;

    public PaymentService(
            PaymentRepositoryPort repository,
            RabbitTemplate rabbitTemplate) {

        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void processPayment(String username) {

        Payment payment =
                new Payment(username, "SUCCESS");

        repository.save(payment);

        rabbitTemplate.convertAndSend(
                "app.exchange",
                "payment.success",
                new PaymentEvent(username)
        );
    }
}