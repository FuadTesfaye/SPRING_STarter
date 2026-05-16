package com.ecommerce.notification.infrastructure.messaging;

import com.ecommerce.notification.application.service.NotificationService;
import com.ecommerce.notification.domain.event.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Listens to ALL events in the system via the notification.queue
 * which is bound to the exchange with wildcard '#'.
 * 
 * Uses a custom message converter + routing key dispatcher to handle
 * different event types from a single queue.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AllEventsConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = "notification.user.queue")
    public void onUserRegistered(UserRegisteredEvent event) {
        notificationService.onUserRegistered(event);
    }

    @RabbitListener(queues = "notification.order.queue")
    public void onOrderCreated(OrderCreatedEvent event) {
        notificationService.onOrderCreated(event);
    }

    @RabbitListener(queues = "notification.payment.completed.queue")
    public void onPaymentCompleted(PaymentCompletedEvent event) {
        notificationService.onPaymentCompleted(event);
    }

    @RabbitListener(queues = "notification.payment.failed.queue")
    public void onPaymentFailed(PaymentFailedEvent event) {
        notificationService.onPaymentFailed(event);
    }

    @RabbitListener(queues = "notification.stock.reserved.queue")
    public void onStockReserved(StockReservedEvent event) {
        notificationService.onStockReserved(event);
    }

    @RabbitListener(queues = "notification.stock.failed.queue")
    public void onStockFailed(StockFailedEvent event) {
        notificationService.onStockFailed(event);
    }

    @RabbitListener(queues = "notification.shipment.queue")
    public void onShipmentCreated(ShipmentCreatedEvent event) {
        notificationService.onShipmentCreated(event);
    }
}
