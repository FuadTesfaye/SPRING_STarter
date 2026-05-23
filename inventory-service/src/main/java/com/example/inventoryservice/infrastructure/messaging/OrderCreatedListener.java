package com.example.inventoryservice.infrastructure.messaging;
import com.example.inventoryservice.application.dto.OrderCreatedMessage;
import com.example.inventoryservice.application.service.ReserveStockUseCase;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {
    private final ReserveStockUseCase useCase;
    public OrderCreatedListener(ReserveStockUseCase u) { this.useCase = u; }
    @RabbitListener(queues = "inventory.queue")
    public void onMessage(OrderCreatedMessage msg) { useCase.handle(msg); }
}
