package com.example.shippingservice.infrastructure.messaging;
import com.example.shippingservice.application.service.CreateShipmentUseCase;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class ShippingListener {
    private final CreateShipmentUseCase useCase;
    private final Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
    public ShippingListener(CreateShipmentUseCase u) { this.useCase = u; }

    @RabbitListener(queues = "shipping.queue")
    public void onMessage(Message msg) {
        String rk = msg.getMessageProperties().getReceivedRoutingKey();
        JsonNode node = (JsonNode) converter.fromMessage(msg, JsonNode.class);
        UUID orderId = UUID.fromString(node.get("orderId").asText());
        if ("payment.completed".equals(rk)) useCase.onPaymentCompleted(orderId);
        else if ("stock.reserved".equals(rk)) useCase.onStockReserved(orderId);
    }
}
