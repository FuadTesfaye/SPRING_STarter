package com.ecom.notification.infrastructure.config;

import com.ecom.notification.application.service.EmailService;
import com.ecom.notification.application.service.NotificationHandler;
import com.ecom.notification.domain.repository.NotificationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public NotificationHandler notificationHandler(NotificationRepository notificationRepository, EmailService emailService) {
        return new NotificationHandler(notificationRepository, emailService);
    }
}
