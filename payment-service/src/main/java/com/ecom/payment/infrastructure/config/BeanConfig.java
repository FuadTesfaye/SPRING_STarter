package com.ecom.payment.infrastructure.config;

import com.ecom.payment.application.port.EventPublisher;
import com.ecom.payment.application.port.PaymentGateway;
import com.ecom.payment.application.service.PaymentProcessor;
import com.ecom.payment.domain.repository.PaymentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public PaymentProcessor paymentProcessor(PaymentRepository paymentRepository, 
                                             EventPublisher eventPublisher, 
                                             PaymentGateway paymentGateway) {
        return new PaymentProcessor(paymentRepository, eventPublisher, paymentGateway);
    }
}
