package com.example.notification.infrastructure.messaging.inbound;

import com.example.notification.application.service.NotificationApplicationService;
import com.example.notification.infrastructure.configuration.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class NotificationMqListener {

    private final NotificationApplicationService notificationApplicationService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NOTIFICATIONS, containerFactory = "rawListenerContainerFactory")
    public void onMessage(Message message) {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        String payload = new String(message.getBody(), StandardCharsets.UTF_8);
        notificationApplicationService.deliver(routingKey, payload);
    }
}
