package com.example.order.application.service;

import com.example.order.domain.model.Order;
import com.example.order.domain.port.OrderRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    private final OrderRepositoryPort repository;
    private final RestTemplate restTemplate;

    public OrderService(OrderRepositoryPort repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public void createOrder(String username) {
        Order order = new Order(username, "CREATED");
        repository.save(order);
        System.out.println("Order created for: " + username);
        restTemplate.postForObject("http://localhost:8083/payments/process?username=" + username, null, String.class);
    }
}
