package com.example.payment.application.service;

import com.example.payment.domain.model.Payment;
import com.example.payment.domain.port.PaymentRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

    private final PaymentRepositoryPort repository;
    private final RestTemplate restTemplate;

    public PaymentService(PaymentRepositoryPort repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public void processPayment(String username) {
        Payment payment = new Payment(username, "SUCCESS");
        repository.save(payment);
        System.out.println("Payment processed for: " + username);
        restTemplate.postForObject("http://localhost:8084/inventory/update?username=" + username, null, String.class);
    }
}
