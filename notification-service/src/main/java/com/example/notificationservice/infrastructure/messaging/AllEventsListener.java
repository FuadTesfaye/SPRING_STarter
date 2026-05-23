package com.example.notificationservice.infrastructure.messaging;
import com.example.notificationservice.application.service.NotificationUseCase;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;

@Component
public class AllEventsListener {
    private final NotificationUseCase useCase;
    public AllEventsListener(NotificationUseCase u) { this.useCase = u; }
    @RabbitListener(queues = "notification.queue")
    public void onMessage(Message msg) {
        String rk = msg.getMessageProperties().getReceivedRoutingKey();
        String body = new String(msg.getBody(), StandardCharsets.UTF_8);
        useCase.handle(rk, body);
    }
}
