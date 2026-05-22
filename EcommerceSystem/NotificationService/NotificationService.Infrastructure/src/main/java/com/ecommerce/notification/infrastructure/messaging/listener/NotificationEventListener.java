package com.ecommerce.notification.infrastructure.messaging.listener;

import com.ecommerce.shared.messaging.event.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationEventListener {

    @RabbitListener(queues = "notification.all.queue")
    public void onEvent(BaseEvent event) {
        log.info("NOTIFICATION RECEIVED: {} - Content: {}", event.getClass().getSimpleName(), event);
        
        if (event instanceof UserRegisteredEvent) {
             System.out.println(">>> User Alert: New User Registered with email " + ((UserRegisteredEvent)event).getEmail());
        } else if (event instanceof OrderCreatedEvent) {
             System.out.println(">>> Order Alert: Order " + ((OrderCreatedEvent)event).getOrderId() + " has been placed.");
        } else if (event instanceof PaymentCompletedEvent) {
             System.out.println(">>> Payment Alert: Payment successful for order " + ((PaymentCompletedEvent)event).getOrderId());
        } else if (event instanceof PaymentFailedEvent) {
             System.out.println(">>> Payment Alert: Payment FAILED for order " + ((PaymentFailedEvent)event).getOrderId());
        } else if (event instanceof StockReservedEvent) {
             System.out.println(">>> Inventory Alert: Stock reserved for order " + ((StockReservedEvent)event).getOrderId());
        } else if (event instanceof StockFailedEvent) {
             System.out.println(">>> Inventory Alert: Stock FAILED for order " + ((StockFailedEvent)event).getOrderId());
        } else if (event instanceof ShipmentCreatedEvent) {
             System.out.println(">>> Shipping Alert: Shipment created for order " + ((ShipmentCreatedEvent)event).getOrderId());
        }
    }
}
