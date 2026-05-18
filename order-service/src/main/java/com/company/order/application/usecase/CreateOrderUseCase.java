package com.company.order.application.usecase;

import com.company.order.domain.model.Order;
import com.company.order.domain.repository.IOrderRepository;
import com.company.order.domain.repository.IOrderProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase {
    private final IOrderRepository orderRepository;
    private final IOrderProducer orderProducer;

    @Transactional
    public Order execute(String customerId, String productId, Integer quantity, BigDecimal pricePerUnit) {
        Order order = new Order(customerId, productId, quantity, pricePerUnit);
        Order savedOrder = orderRepository.save(order);

        // Send to RabbitMQ via the Producer
        orderProducer.sendOrderCreatedEvent(savedOrder);

        return savedOrder;
    }
}
