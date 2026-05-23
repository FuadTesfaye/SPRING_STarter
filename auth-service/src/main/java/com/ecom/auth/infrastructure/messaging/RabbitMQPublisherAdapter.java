package com.ecom.auth.infrastructure.messaging;

import com.ecom.auth.application.port.EventPublisher;
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

    @Value("${services.notification-service.url:http://localhost:8085}")
    private String notificationServiceUrl;

    public RabbitMQPublisherAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void publishUserRegistered(Object event) {
        CompletableFuture.runAsync(() -> {
            try {
                String payload = objectMapper.writeValueAsString(event);
                Map<String, String> request = Map.of(
                    "eventType", "user.registered",
                    "payload", payload
                );
                restTemplate.postForObject(notificationServiceUrl + "/api/notifications", request, String.class);
            } catch (Exception e) {
                System.err.println("HTTP post to notification-service failed: " + e.getMessage());
            }
        });
    }
}
