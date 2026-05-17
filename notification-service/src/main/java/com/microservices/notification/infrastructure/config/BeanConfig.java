package com.microservices.notification.infrastructure.config;

import com.microservices.notification.application.service.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    
    @Bean
    public NotificationService notificationService() {
        return new NotificationService();
    }
}
