# Event-Driven Microservices System

A distributed system built with Spring Boot, RabbitMQ, and Onion Architecture.

## Services
- **Auth Service**: User registration, login, JWT generation
- **Order Service**: Order creation and management
- **Payment Service**: Mock payment processing
- **Inventory Service**: Stock management
- **Shipping Service**: Shipment creation
- **Notification Service**: Event logging and notifications

## Architecture
- Onion Architecture (Domain, Application, Infrastructure, Presentation)
- Event-driven communication via RabbitMQ
- Independent databases per service

## Tech Stack
- Java 17
- Spring Boot 3.2
- RabbitMQ
- PostgreSQL / H2
- Docker
- Swagger/OpenAPI
