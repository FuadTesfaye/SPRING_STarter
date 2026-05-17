package com.example.notification.infrastructure.messaging;

import com.example.notification.application.service.NotificationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {
    private static final Logger log = LoggerFactory.getLogger(EventConsumer.class);
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    public EventConsumer(NotificationService notificationService, ObjectMapper objectMapper) {
        this.notificationService = notificationService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "notification.all.queue")
    public void handleAllEvents(String message) {
        try {
            JsonNode event = objectMapper.readTree(message);
            String eventType = "Unknown";
            
            if (event.has("eventType")) {
                eventType = event.get("eventType").asText();
            } else if (event.has("username")) {
                eventType = "UserRegistered";
            } else if (event.has("totalAmount") && event.has("items")) {
                eventType = "OrderCreated";
            } else if (event.has("trackingNumber")) {
                eventType = "ShipmentCreated";
            }
            
            notificationService.notify(eventType, message);
        } catch (Exception e) {
            log.error("Error processing event: {}", e.getMessage());
        }
    }
}