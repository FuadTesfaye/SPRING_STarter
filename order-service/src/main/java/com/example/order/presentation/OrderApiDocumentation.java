package com.example.order.presentation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI/Swagger Documentation for Order Service
 * 
 * Access at: http://localhost:8082/swagger-ui.html
 * API Docs: http://localhost:8082/v3/api-docs
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Order Service API",
                version = "1.0.0",
                description = "Order management service for the distributed event-driven system. " +
                        "Handles order creation and retrieval.",
                contact = @Contact(
                        name = "Order Service Team",
                        email = "order@example.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8082",
                        description = "Local Development Server"
                ),
                @Server(
                        url = "http://order-service:8082",
                        description = "Docker Network"
                )
        }
)
public class OrderApiDocumentation {
}
