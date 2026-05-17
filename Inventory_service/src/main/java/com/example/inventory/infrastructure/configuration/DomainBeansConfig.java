package com.example.inventory.infrastructure.configuration;

import com.example.inventory.domain.service.StockRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainBeansConfig {

    @Bean
    public StockRegistry stockRegistry() {
        return new StockRegistry();
    }
}
