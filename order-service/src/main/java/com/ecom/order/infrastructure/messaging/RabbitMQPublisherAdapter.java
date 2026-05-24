package com.ecom.order.infrastructure.messaging;

import com.ecom.order.application.dto.OrderCreatedEvent;
import com.ecom.order.application.port.EventPublisher;
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

    @Value("${services.payment-service.url:http://localhost:8082}")
    private String paymentServiceUrl;

    @Value("${services.inventory-service.url:http://localhost:8083}")
    private String inventoryServiceUrl;

    @Value("${services.notification-service.url:http://localhost:8085}")
    private String notificationServiceUrl;

    public RabbitMQPublisherAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void publishOrderCreated(OrderCreatedEvent event) {
        CompletableFuture.runAsync(() -> {
            try {
                // 1. Notify payment-service
                try {
                    restTemplate.postForObject(paymentServiceUrl + "/api/events/order-created", event, String.class);
                } catch (Exception ex) {
                    System.err.println("[order-service] Failed to call payment-service: " + ex.getMessage());
                }

                // 2. Notify inventory-service
                try {
                    restTemplate.postForObject(inventoryServiceUrl + "/api/events/order-created", event, String.class);
                } catch (Exception ex) {
                    System.err.println("[order-service] Failed to call inventory-service: " + ex.getMessage());
                }

                // 3. Notify notification-service
                try {
                    String payload = objectMapper.writeValueAsString(event);
                    Map<String, String> request = Map.of(
                        "eventType", "order.created",
                        "payload", payload
                    );
                    restTemplate.postForObject(notificationServiceUrl + "/api/notifications", request, String.class);
                } catch (Exception ex) {
                    System.err.println("[order-service] Failed to call notification-service: " + ex.getMessage());
                }
            } catch (Exception ex) {
                System.err.println("[order-service] Error processing order events: " + ex.getMessage());
            }
        });
    }
}
