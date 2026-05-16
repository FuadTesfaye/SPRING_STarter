package com.example.auth.presentation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI/Swagger Documentation for Auth Service
 * 
 * Access at: http://localhost:8081/swagger-ui.html
 * API Docs: http://localhost:8081/v3/api-docs
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Auth Service API",
                version = "1.0.0",
                description = "Authentication and authorization service for the distributed event-driven system. " +
                        "Handles user registration, login, and JWT token generation.",
                contact = @Contact(
                        name = "Auth Service Team",
                        email = "auth@example.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8081",
                        description = "Local Development Server"
                ),
                @Server(
                        url = "http://auth-service:8081",
                        description = "Docker Network"
                )
        }
)
public class AuthApiDocumentation {
}
