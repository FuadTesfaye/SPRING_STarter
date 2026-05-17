package com.example.shipping.application.service;

import com.example.shipping.domain.model.Shipping;
import com.example.shipping.domain.port.ShippingRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ShippingService {

    private final ShippingRepositoryPort repository;
    private final RestTemplate restTemplate;

    public ShippingService(ShippingRepositoryPort repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public void createShipping(String username) {
        Shipping shipping = new Shipping(username, "SHIPPED");
        repository.save(shipping);
        System.out.println("Shipping created for: " + username);
        restTemplate.postForObject("http://localhost:8086/notifications/send?username=" + username, null, String.class);
    }
}
