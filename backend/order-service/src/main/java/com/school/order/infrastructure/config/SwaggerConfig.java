package com.school.order.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI orderOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Order Service API")
                .description("Campus Commerce System - Order Service")
                .version("1.0.0"));
    }
}
