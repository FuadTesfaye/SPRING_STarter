package com.microservices.order.infrastructure.config;

import com.microservices.order.domain.event.OrderEventPublisher;
import com.microservices.order.domain.repository.OrderRepository;
import com.microservices.order.domain.service.OrderDomainService;
import com.microservices.order.application.service.OrderApplicationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * BEAN CONFIGURATION
 * 
 * WHAT: Manually creates Spring beans
 * WHY: Keeps domain layer pure (no Spring annotations)
 */
@Configuration
public class BeanConfig {
    
    @Bean
    public OrderDomainService orderDomainService(
            OrderRepository orderRepository,
            OrderEventPublisher eventPublisher) {
        return new OrderDomainService(orderRepository, eventPublisher);
    }
    
    @Bean
    public OrderApplicationService orderApplicationService(
            OrderDomainService orderDomainService) {
        return new OrderApplicationService(orderDomainService);
    }
}
