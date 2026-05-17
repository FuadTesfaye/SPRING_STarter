package com.microservices.payment.infrastructure.config;

import com.microservices.payment.application.service.PaymentApplicationService;
import com.microservices.payment.domain.event.PaymentEventPublisher;
import com.microservices.payment.domain.repository.PaymentRepository;
import com.microservices.payment.domain.service.PaymentDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * BEAN CONFIGURATION
 * 
 * WHAT: Wire all dependencies manually
 * WHY: Keep domain layer pure (no Spring annotations)
 */
@Configuration
public class BeanConfig {
    
    @Bean
    public PaymentDomainService paymentDomainService(
            PaymentRepository paymentRepository,
            PaymentEventPublisher eventPublisher) {
        return new PaymentDomainService(paymentRepository, eventPublisher);
    }
    
    @Bean
    public PaymentApplicationService paymentApplicationService(
            PaymentDomainService paymentDomainService) {
        return new PaymentApplicationService(paymentDomainService);
    }
}
