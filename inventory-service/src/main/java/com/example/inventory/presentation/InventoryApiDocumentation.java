package com.example.inventory.presentation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI/Swagger Documentation for Inventory Service
 * 
 * Access at: http://localhost:8084/swagger-ui.html
 * API Docs: http://localhost:8084/v3/api-docs
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Inventory Service API",
                version = "1.0.0",
                description = "Inventory management service for the distributed event-driven system. " +
                        "Handles stock management and reservation for orders.",
                contact = @Contact(
                        name = "Inventory Service Team",
                        email = "inventory@example.com"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8084",
                        description = "Local Development Server"
                ),
                @Server(
                        url = "http://inventory-service:8084",
                        description = "Docker Network"
                )
        }
)
public class InventoryApiDocumentation {
}
