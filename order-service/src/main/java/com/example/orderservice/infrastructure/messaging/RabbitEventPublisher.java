package com.example.orderservice.infrastructure.messaging;
import com.example.orderservice.application.port.EventPublisherPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component
public class RabbitEventPublisher implements EventPublisherPort {
    private final RabbitTemplate rabbitTemplate;
    @Value("${app.exchange}") private String exchange;
    public RabbitEventPublisher(RabbitTemplate rt) { this.rabbitTemplate = rt; }
    @Override public void publish(String routingKey, Object payload) {
        rabbitTemplate.convertAndSend(exchange, routingKey, payload);
    }
}
