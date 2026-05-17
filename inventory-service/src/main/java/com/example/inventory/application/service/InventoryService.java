package com.example.inventory.application.service;

import com.example.inventory.domain.model.Inventory;
import com.example.inventory.domain.port.InventoryRepositoryPort;
import com.example.inventory.infrastructure.messaging.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final InventoryRepositoryPort repository;
    private final RabbitTemplate rabbitTemplate;

    public InventoryService(InventoryRepositoryPort repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void updateInventory(String username) {
        Inventory inventory = new Inventory(username, "UPDATED");
        repository.save(inventory);
        System.out.println("Inventory updated for: " + username);
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.INVENTORY_UPDATED_EXCHANGE,
            RabbitMQConfig.INVENTORY_UPDATED_ROUTING_KEY,
            username
        );
    }
}
