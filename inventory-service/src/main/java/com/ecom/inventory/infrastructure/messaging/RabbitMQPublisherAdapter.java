package com.ecom.inventory.infrastructure.messaging;

import com.ecom.inventory.application.dto.StockReservedEvent;
import com.ecom.inventory.application.port.EventPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class RabbitMQPublisherAdapter implements EventPublisher {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    
    @Value("${services.shipping-service.url:http://localhost:8084}")
    private String shippingServiceUrl;

    @Value("${services.notification-service.url:http://localhost:8085}")
    private String notificationServiceUrl;

    public RabbitMQPublisherAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void publishStockReserved(StockReservedEvent event) {
        CompletableFuture.runAsync(() -> {
            try {
                // 1. Notify shipping service if reserved
                if ("RESERVED".equalsIgnoreCase(event.status())) {
                    Map<String, Object> shippingPayload = Map.of("orderId", event.orderId());
                    try {
                        restTemplate.postForObject(shippingServiceUrl + "/api/events/stock-reserved", shippingPayload, String.class);
                    } catch (Exception ex) {
                        System.err.println("[inventory-service] Failed to call shipping-service: " + ex.getMessage());
                    }
                }

                // 2. Notify notification service
                String routingKey = event.status().equalsIgnoreCase("RESERVED") ? "stock.reserved" : "stock.failed";
                String notificationPayload = objectMapper.writeValueAsString(event);
                Map<String, String> notificationRequest = Map.of(
                    "eventType", routingKey,
                    "payload", notificationPayload
                );
                try {
                    restTemplate.postForObject(notificationServiceUrl + "/api/notifications", notificationRequest, String.class);
                } catch (Exception ex) {
                    System.err.println("[inventory-service] Failed to call notification-service: " + ex.getMessage());
                }
            } catch (Exception ex) {
                System.err.println("[inventory-service] Error processing events: " + ex.getMessage());
            }
        });
    }
}
