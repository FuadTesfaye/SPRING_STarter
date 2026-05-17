package com.example.notificationservice.infrastructure.config;

import com.example.notificationservice.application.usecases.SendNotificationService;
import com.example.notificationservice.application.usecases.SendNotificationUseCase;
import com.example.notificationservice.domain.interfaces.NotificationRepository;
import com.example.notificationservice.domain.services.NotificationDomainService;
import com.example.notificationservice.infrastructure.persistence.adapter.NotificationPersistenceAdapter;
import com.example.notificationservice.infrastructure.persistence.adapter.NotificationPersistenceMapper;
import com.example.notificationservice.infrastructure.persistence.repository.SpringDataNotificationRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfiguration {

    @Bean
    public NotificationDomainService notificationDomainService() {
        return new NotificationDomainService();
    }

    @Bean
    public NotificationPersistenceMapper notificationPersistenceMapper() {
        return new NotificationPersistenceMapper();
    }

    @Bean
    public NotificationRepository notificationRepository(
            SpringDataNotificationRepository springDataNotificationRepository,
            NotificationPersistenceMapper notificationPersistenceMapper
    ) {
        return new NotificationPersistenceAdapter(springDataNotificationRepository, notificationPersistenceMapper);
    }

    @Bean
    public SendNotificationService sendNotificationUseCase(
            NotificationRepository notificationRepository,
            NotificationDomainService notificationDomainService
    ) {
        return new SendNotificationUseCase(notificationRepository, notificationDomainService);
    }
}
