package com.school.notification.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI notificationOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Notification Service API")
                .description("Listens to all domain events and logs notifications")
                .version("1.0.0"));
    }
}
