package com.company.inventory.application.service;

import com.company.inventory.domain.event.ShadeBackInStockEvent;
import com.company.inventory.domain.model.ShadeInventory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShadeInventoryService {

    private final RabbitTemplate rabbitTemplate;
    // Map to simulate repository for Restock Subscriptions
    private final Map<String, List<String>> restockSubscriptions = new HashMap<>();

    public ShadeInventoryService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public ShadeInventory getShadeStock(String shadeCode) {
        ShadeInventory inventory = new ShadeInventory();
        inventory.setShadeCode(shadeCode);
        inventory.setQuantity(100);
        return inventory;
    }

    public void subscribeToRestock(String userId, String shadeCode) {
        restockSubscriptions.computeIfAbsent(shadeCode, k -> new ArrayList<>()).add(userId);
    }

    public List<ShadeInventory> getExpiringSoon(int days) {
        LocalDate threshold = LocalDate.now().plusDays(days);
        // Simulate fetching from DB
        return new ArrayList<>();
    }

    @Scheduled(cron = "0 0 0 * * ?") // Run daily at midnight
    public void checkAndEmitRestockAlerts() {
        // Logic to check DB and emit alerts
        // For demonstration, let's pretend a shade just crossed threshold upwards
        ShadeBackInStockEvent event = new ShadeBackInStockEvent("prod-1", "SHADE-01", 50);
        rabbitTemplate.convertAndSend("beauty.events", "shade.back.in.stock", event);
    }

    public boolean deductShadeStock(String shadeCode, int quantity) {
        // Simulated deduction logic
        return true;
    }
}
