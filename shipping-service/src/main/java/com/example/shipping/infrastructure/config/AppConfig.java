package com.example.shipping.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public OpenAPI shippingServiceOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("Shipping Service API").description("Handles shipping creation").version("1.0"));
    }
}
