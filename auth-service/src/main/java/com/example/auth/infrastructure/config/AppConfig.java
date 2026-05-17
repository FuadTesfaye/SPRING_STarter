package com.example.auth.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public OpenAPI authServiceOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Auth Service API")
                .description("Handles user registration")
                .version("1.0"));
    }
}
