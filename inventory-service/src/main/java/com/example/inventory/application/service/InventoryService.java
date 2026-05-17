package com.example.inventory.application.service;

import com.example.inventory.domain.model.Inventory;
import com.example.inventory.domain.port.InventoryRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InventoryService {

    private final InventoryRepositoryPort repository;
    private final RestTemplate restTemplate;

    public InventoryService(InventoryRepositoryPort repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public void updateInventory(String username) {
        Inventory inventory = new Inventory(username, "UPDATED");
        repository.save(inventory);
        System.out.println("Inventory updated for: " + username);
        restTemplate.postForObject("http://localhost:8085/shipping/create?username=" + username, null, String.class);
    }
}
