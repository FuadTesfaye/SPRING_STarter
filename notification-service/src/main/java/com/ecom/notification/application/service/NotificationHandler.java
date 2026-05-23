package com.ecom.notification.application.service;

import com.ecom.notification.domain.model.Notification;
import com.ecom.notification.domain.repository.NotificationRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class NotificationHandler {
    private static final Logger logger = LoggerFactory.getLogger(NotificationHandler.class);
    private final NotificationRepository notificationRepository;
    private final EmailService emailService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public NotificationHandler(NotificationRepository notificationRepository, EmailService emailService) {
        this.notificationRepository = notificationRepository;
        this.emailService = emailService;
    }

    public void processEvent(String eventType, String payload) {
        logger.info("Received event [{}]: {}", eventType, payload);

        Notification notification = new Notification(
                UUID.randomUUID(),
                eventType,
                payload,
                LocalDateTime.now()
        );

        notificationRepository.save(notification);

        // Check for checkout or payment success events
        if ("ORDER_PLACED".equalsIgnoreCase(eventType) || "order.created".equalsIgnoreCase(eventType) || "payment.completed".equalsIgnoreCase(eventType)) {
            try {
                JsonNode rootNode = objectMapper.readTree(payload);
                
                String orderId = rootNode.has("orderId") ? rootNode.get("orderId").asText() : "N/A";
                String totalAmount = rootNode.has("totalAmount") ? rootNode.get("totalAmount").asText() : "0.00";
                String customerEmail = rootNode.has("customerEmail") ? rootNode.get("customerEmail").asText() : "dndagi2424@gmail.com";
                String customerName = rootNode.has("customerName") ? rootNode.get("customerName").asText() : "Valued Customer";

                // Map 'amount' as fallback for payment success event payloads
                if (rootNode.has("amount") && "0.00".equals(totalAmount)) {
                    totalAmount = rootNode.get("amount").asText();
                }

                logger.info("Triggering order/payment confirmation email to {} for Order ID {}", customerEmail, orderId);
                emailService.sendOrderConfirmationEmail(customerEmail, customerName, orderId, totalAmount);
            } catch (Exception e) {
                logger.error("Failed to parse event payload or trigger order confirmation email for event: {}", eventType, e);
            }
        }
    }
}
