package com.assignment.inventory.application.service;

import com.assignment.inventory.application.dto.StockCheckRequest;
import com.assignment.inventory.application.dto.StockResponse;
import com.assignment.inventory.domain.model.InventoryItem;
import com.assignment.inventory.domain.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class InventoryService {
    
    private final InventoryRepository inventoryRepository;
    private final RestClient restClient;
    
    @Value("${services.notification}")
    private String notificationServiceUrl;
    
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
        this.restClient = RestClient.create();
        
        // Seed some products
        if (inventoryRepository.count() == 0) {
            inventoryRepository.save(new InventoryItem("Laptop", 10));
            inventoryRepository.save(new InventoryItem("Phone", 5));
            inventoryRepository.save(new InventoryItem("Mouse", 0));
            System.out.println("📦 Sample inventory added: Laptop(10), Phone(5), Mouse(0)");
        }
    }
    
    public StockResponse checkStock(StockCheckRequest request) {
        StockResponse response = new StockResponse();
        
        InventoryItem item = inventoryRepository.findByProductName(request.getProductName()).orElse(null);
        
        if (item == null) {
            response.setAvailable(false);
            response.setMessage("Product not found: " + request.getProductName());
            response.setAvailableQuantity(0);
        } else if (item.getAvailableQuantity() >= request.getQuantity()) {
            response.setAvailable(true);
            response.setMessage("Stock available");
            response.setAvailableQuantity(item.getAvailableQuantity());
            
            // Reduce stock
            item.setAvailableQuantity(item.getAvailableQuantity() - request.getQuantity());
            inventoryRepository.save(item);
            
            System.out.println("✅ Stock reserved for: " + request.getProductName());
        } else {
            response.setAvailable(false);
            response.setMessage("Insufficient stock. Available: " + item.getAvailableQuantity());
            response.setAvailableQuantity(item.getAvailableQuantity());
            
            System.out.println("❌ Stock insufficient for: " + request.getProductName());
        }
        
        sendNotification(request.getProductName(), 
            response.isAvailable() ? "Stock reserved" : "Stock failed");
        
        return response;
    }
    
    private void sendNotification(String productName, String message) {
        try {
            Map<String, Object> notification = new HashMap<>();
            notification.put("service", "inventory-service");
            notification.put("event", message);
            notification.put("productName", productName);
            notification.put("timestamp", System.currentTimeMillis());
            
            restClient.post()
                .uri(notificationServiceUrl + "/api/notifications/log")
                .body(notification)
                .retrieve();
            
        } catch (Exception e) {
            // Notification service may not be running yet
        }
    }
}