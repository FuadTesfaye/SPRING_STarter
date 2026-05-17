package com.microservices.notification.infrastructure.messaging;

import com.microservices.notification.application.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * NOTIFICATION EVENT LISTENER
 * 
 * WHAT: Listens to ALL system events and logs notifications
 * WHY: Demonstrates complete event-driven communication
 * 
 * LISTENS TO 7 DIFFERENT EVENTS!
 */
@Component
public class NotificationEventListener {
    
    private static final Logger log = LoggerFactory.getLogger(NotificationEventListener.class);
    private final NotificationService notificationService;
    
    public NotificationEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    
    @RabbitListener(queues = "${app.rabbitmq.queue.user-registered}")
    public void handleUserRegistered(UserRegisteredEvent event) {
        log.info("🔔 Received: user.registered");
        notificationService.notifyUserRegistered(
            event.getUserId(), event.getEmail(), event.getUsername());
    }
    
    @RabbitListener(queues = "${app.rabbitmq.queue.order-created}")
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("🔔 Received: order.created");
        notificationService.notifyOrderCreated(event.getOrderId(), event.getUserId());
    }
    
    @RabbitListener(queues = "${app.rabbitmq.queue.payment-completed}")
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        log.info("🔔 Received: payment.completed");
        notificationService.notifyPaymentCompleted(event.getPaymentId(), event.getOrderId());
    }
    
    @RabbitListener(queues = "${app.rabbitmq.queue.payment-failed}")
    public void handlePaymentFailed(PaymentFailedEvent event) {
        log.info("🔔 Received: payment.failed");
        notificationService.notifyPaymentFailed(
            event.getPaymentId(), event.getOrderId(), event.getReason());
    }
    
    @RabbitListener(queues = "${app.rabbitmq.queue.stock-reserved}")
    public void handleStockReserved(StockReservedEvent event) {
        log.info("🔔 Received: stock.reserved");
        notificationService.notifyStockReserved(
            event.getReservationId(), event.getOrderId(), event.getProductName());
    }
    
    @RabbitListener(queues = "${app.rabbitmq.queue.stock-failed}")
    public void handleStockFailed(StockFailedEvent event) {
        log.info("🔔 Received: stock.failed");
        notificationService.notifyStockFailed(
            event.getReservationId(), event.getOrderId(), event.getReason());
    }
    
    @RabbitListener(queues = "${app.rabbitmq.queue.shipment-created}")
    public void handleShipmentCreated(ShipmentCreatedEvent event) {
        log.info("🔔 Received: shipment.created");
        notificationService.notifyShipmentCreated(
            event.getShipmentId(), event.getOrderId(), event.getTrackingNumber());
    }
}
