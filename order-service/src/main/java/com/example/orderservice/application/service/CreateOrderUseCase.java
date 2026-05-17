package com.example.orderservice.application.service;
import com.example.orderservice.application.port.*;
import com.example.orderservice.domain.event.OrderCreatedEvent;
import com.example.orderservice.domain.model.Order;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
public class CreateOrderUseCase {
    private final OrderRepositoryPort orders;
    private final EventPublisherPort publisher;
    public CreateOrderUseCase(OrderRepositoryPort o, EventPublisherPort p) { this.orders=o; this.publisher=p; }

    public Order create(UUID userId, String sku, int qty, BigDecimal amount) {
        if (qty <= 0) throw new IllegalArgumentException("quantity must be > 0");
        Order o = new Order(UUID.randomUUID(), userId, sku, qty, amount, Instant.now());
        Order saved = orders.save(o);
        publisher.publish("order.created",
            new OrderCreatedEvent(saved.getId(), saved.getUserId(), saved.getProductSku(),
                                  saved.getQuantity(), saved.getAmount(), Instant.now()));
        return saved;
    }
}
