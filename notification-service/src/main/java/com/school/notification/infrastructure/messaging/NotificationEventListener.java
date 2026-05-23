package com.school.notification.infrastructure.messaging;

import com.school.notification.application.service.NotificationService;
import com.school.notification.domain.event.GenericEventDto;
import com.school.notification.infrastructure.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = RabbitMQConfig.NOTIF_USER_QUEUE)
    public void onUserRegistered(GenericEventDto event) {
        notificationService.handleUserRegistered(event);
    }

    @RabbitListener(queues = RabbitMQConfig.NOTIF_ORDER_QUEUE)
    public void onOrderCreated(GenericEventDto event) {
        notificationService.handleOrderCreated(event);
    }

    @RabbitListener(queues = RabbitMQConfig.NOTIF_PAYMENT_COMPLETED_QUEUE)
    public void onPaymentCompleted(GenericEventDto event) {
        notificationService.handlePaymentCompleted(event);
    }

    @RabbitListener(queues = RabbitMQConfig.NOTIF_PAYMENT_FAILED_QUEUE)
    public void onPaymentFailed(GenericEventDto event) {
        notificationService.handlePaymentFailed(event);
    }

    @RabbitListener(queues = RabbitMQConfig.NOTIF_STOCK_RESERVED_QUEUE)
    public void onStockReserved(GenericEventDto event) {
        notificationService.handleStockReserved(event);
    }

    @RabbitListener(queues = RabbitMQConfig.NOTIF_STOCK_FAILED_QUEUE)
    public void onStockFailed(GenericEventDto event) {
        notificationService.handleStockFailed(event);
    }

    @RabbitListener(queues = RabbitMQConfig.NOTIF_SHIPMENT_QUEUE)
    public void onShipmentCreated(GenericEventDto event) {
        notificationService.handleShipmentCreated(event);
    }
}
