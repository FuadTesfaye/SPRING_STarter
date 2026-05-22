package com.ecommerce.notification.infrastructure.messaging.listener;

import com.ecommerce.shared.messaging.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationEventListener {

    private final MessageConverter messageConverter;

    public NotificationEventListener(MessageConverter messageConverter) {
        this.messageConverter = messageConverter;
    }

    @RabbitListener(queues = "notification.queue")
    public void onMessage(Message message) {
        try {
            Object event = messageConverter.fromMessage(message);
            log.info("NOTIFICATION RECEIVED: {} - Content: {}", event.getClass().getSimpleName(), event);

            if (event instanceof UserRegisteredEvent e) {
                System.out.println(">>> User Alert: New User Registered with email " + e.getEmail());
            } else if (event instanceof UserLoggedInEvent e) {
                System.out.println(">>> User Alert: User logged in with email " + e.getEmail());
            } else if (event instanceof OrderCreatedEvent e) {
                System.out.println(">>> Order Alert: Order " + e.getOrderId() + " has been placed.");
            } else if (event instanceof PaymentCompletedEvent e) {
                System.out.println(">>> Payment Alert: Payment successful for order " + e.getOrderId());
            } else if (event instanceof PaymentFailedEvent e) {
                System.out.println(">>> Payment Alert: Payment FAILED for order " + e.getOrderId());
            } else if (event instanceof StockReservedEvent e) {
                System.out.println(">>> Inventory Alert: Stock reserved for order " + e.getOrderId());
            } else if (event instanceof StockFailedEvent e) {
                System.out.println(">>> Inventory Alert: Stock FAILED for order " + e.getOrderId());
            } else if (event instanceof ShipmentCreatedEvent e) {
                System.out.println(">>> Shipping Alert: Shipment created for order " + e.getOrderId());
            } else {
                System.out.println(">>> Unknown event received: " + event.getClass().getSimpleName());
            }
        } catch (Exception ex) {
            log.error("Failed to process notification message: {}", ex.getMessage(), ex);
        }
    }
}
