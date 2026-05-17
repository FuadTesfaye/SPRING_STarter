package com.example.payment.infrastructure.configuration;

import com.example.payment.domain.service.PaymentPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainBeansConfig {

    @Bean
    public PaymentPolicy paymentPolicy() {
        return new PaymentPolicy();
    }
}
