package com.example.notification.infrastructure.messaging;

import com.example.events.OrderCreatedEvent;
import com.example.events.PaymentCompletedEvent;
import com.example.events.PaymentFailedEvent;
import com.example.events.QueueNames;
import com.example.events.ShipmentCreatedEvent;
import com.example.events.StockFailedEvent;
import com.example.events.StockReservedEvent;
import com.example.events.UserRegisteredEvent;
import com.example.notification.application.service.NotificationApplicationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventListener {

    private final NotificationApplicationService notificationApplicationService;

    public NotificationEventListener(NotificationApplicationService notificationApplicationService) {
        this.notificationApplicationService = notificationApplicationService;
    }

    @RabbitListener(queues = QueueNames.NOTIFICATION_EVENTS_QUEUE)
    public void handleUserRegistered(UserRegisteredEvent event) {
        notificationApplicationService.notifyUserRegistered(event);
    }

    @RabbitListener(queues = QueueNames.NOTIFICATION_EVENTS_QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {
        notificationApplicationService.notifyOrderCreated(event);
    }

    @RabbitListener(queues = QueueNames.NOTIFICATION_EVENTS_QUEUE)
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        notificationApplicationService.notifyPaymentCompleted(event);
    }

    @RabbitListener(queues = QueueNames.NOTIFICATION_EVENTS_QUEUE)
    public void handlePaymentFailed(PaymentFailedEvent event) {
        notificationApplicationService.notifyPaymentFailed(event);
    }

    @RabbitListener(queues = QueueNames.NOTIFICATION_EVENTS_QUEUE)
    public void handleStockReserved(StockReservedEvent event) {
        notificationApplicationService.notifyStockReserved(event);
    }

    @RabbitListener(queues = QueueNames.NOTIFICATION_EVENTS_QUEUE)
    public void handleStockFailed(StockFailedEvent event) {
        notificationApplicationService.notifyStockFailed(event);
    }

    @RabbitListener(queues = QueueNames.NOTIFICATION_EVENTS_QUEUE)
    public void handleShipmentCreated(ShipmentCreatedEvent event) {
        notificationApplicationService.notifyShipmentCreated(event);
    }
}
