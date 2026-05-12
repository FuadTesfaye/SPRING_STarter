package com.microservices.notificationservice.infrastructure.messaging;

import com.microservices.notificationservice.application.event.ShippingEvent;
import com.microservices.notificationservice.application.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    private final NotificationService notificationService;

    public NotificationListener(
            NotificationService notificationService) {

        this.notificationService =
                notificationService;
    }

    @RabbitListener(
            queues = "shipping.created.queue")
    public void handle(
            ShippingEvent event) {

        notificationService.sendNotification(
                event.getUsername());
    }
}