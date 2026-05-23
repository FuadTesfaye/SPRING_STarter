# Ecommerce Event-Driven System

A complete distributed event-driven ecommerce backend system using Java 21, Spring Boot 3, RabbitMQ, PostgreSQL, Docker, and strict Onion Architecture.

## Architecture Overview

This system demonstrates enterprise-grade microservices architecture with the following principles:

- **Event-Driven Communication**: All services communicate through RabbitMQ events only
- **Onion Architecture**: Each service strictly follows layered architecture
- **No REST calls between services**: All inter-service communication via RabbitMQ
- **Separate Databases**: Each service has its own PostgreSQL database
- **Docker Containerization**: All services containerized with Docker

## Services

### 1. Auth Service (Port 8081)
- **Responsibilities**: User registration, login, JWT generation and validation
- **Database**: `authdb`
- **Publishes**: `user.registered`
- **Endpoints**:
  - `POST /auth/register` - Register new user
  - `POST /auth/login` - User login
- **Swagger**: http://localhost:8081/swagger-ui.html

### 2. Order Service (Port 8082)
- **Responsibilities**: Order creation and management
- **Database**: `orderdb`
- **Publishes**: `order.created`
- **Consumes**: JWT validation
- **Endpoints**:
  - `POST /orders` - Create new order (requires JWT)
  - `GET /orders/{id}` - Get order by ID (requires JWT)
- **Swagger**: http://localhost:8082/swagger-ui.html

### 3. Payment Service (Port 8083)
- **Responsibilities**: Payment processing with 70% success rate
- **Database**: `paymentdb`
- **Consumes**: `order.created`
- **Publishes**: `payment.completed`, `payment.failed`
- **Endpoints**:
  - `GET /payments/{id}` - Get payment by ID
  - `GET /payments/order/{orderId}` - Get payment by order ID
- **Swagger**: http://localhost:8083/swagger-ui.html

### 4. Inventory Service (Port 8084)
- **Responsibilities**: Stock management with 80% reservation success rate
- **Database**: `inventorydb`
- **Consumes**: `order.created`
- **Publishes**: `stock.reserved`, `stock.failed`
- **Endpoints**:
  - `GET /inventory/{id}` - Get inventory by ID
  - `GET /inventory/product/{productId}` - Get inventory by product ID
- **Swagger**: http://localhost:8084/swagger-ui.html

### 5. Shipping Service (Port 8085)
- **Responsibilities**: Shipment creation after payment and stock confirmation
- **Database**: `shippingdb`
- **Consumes**: `payment.completed`, `stock.reserved`
- **Publishes**: `shipment.created`
- **Logic**: Waits for BOTH payment and stock events before creating shipment
- **Endpoints**:
  - `GET /shipments/{id}` - Get shipment by ID
  - `GET /shipments/order/{orderId}` - Get shipment by order ID
- **Swagger**: http://localhost:8085/swagger-ui.html

### 6. Notification Service (Port 8086)
- **Responsibilities**: Event logging to console and file
- **Database**: None
- **Consumes**: ALL events (`user.registered`, `order.created`, `payment.completed`, `payment.failed`, `stock.reserved`, `stock.failed`, `shipment.created`)
- **Endpoints**:
  - `GET /notifications` - Get all notifications
  - `GET /notifications/health` - Health check
- **Swagger**: http://localhost:8086/swagger-ui.html

## RabbitMQ Events

All services communicate through a topic exchange with the following routing keys:

- `user.registered` - User registration completed
- `order.created` - New order created
- `payment.completed` - Payment processed successfully
- `payment.failed` - Payment processing failed
- `stock.reserved` - Stock reserved successfully
- `stock.failed` - Stock reservation failed
- `shipment.created` - Shipment created

## Database Schema

Each service maintains its own PostgreSQL database:

- **authdb**: Users table with authentication data
- **orderdb**: Orders and OrderItems tables
- **paymentdb**: Payments table with transaction tracking
- **inventorydb**: Inventory table with stock management
- **shippingdb**: Shipments and FulfillmentStates tables

## Quick Start

### Prerequisites

- Docker and Docker Compose
- Java 21 (for local development)
- Maven 3.8+ (for local development)

### Running the System

1. **Clone and build all services**:
   ```bash
   cd Backend
   mvn clean install -DskipTests
   ```

2. **Start all services with Docker Compose**:
   ```bash
   docker-compose up --build
   ```

3. **Access services**:
   - RabbitMQ Management: http://localhost:15672 (guest/guest)
   - Auth Service: http://localhost:8081/swagger-ui.html
   - Order Service: http://localhost:8082/swagger-ui.html
   - Payment Service: http://localhost:8083/swagger-ui.html
   - Inventory Service: http://localhost:8084/swagger-ui.html
   - Shipping Service: http://localhost:8085/swagger-ui.html
   - Notification Service: http://localhost:8086/swagger-ui.html

### Testing the Flow

1. **Register a user**:
   ```bash
   curl -X POST http://localhost:8081/auth/register \
     -H "Content-Type: application/json" \
     -d '{
       "username": "testuser",
       "email": "test@example.com",
       "password": "password123"
     }'
   ```

2. **Login to get JWT**:
   ```bash
   curl -X POST http://localhost:8081/auth/login \
     -H "Content-Type: application/json" \
     -d '{
       "username": "testuser",
       "password": "password123"
     }'
   ```

3. **Create an order** (replace JWT_TOKEN):
   ```bash
   curl -X POST http://localhost:8082/orders \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer JWT_TOKEN" \
     -d '{
       "userId": 1,
       "items": [{
         "productId": "PROD-001",
         "productName": "Test Product",
         "quantity": 2,
         "unitPrice": 29.99
       }]
     }'
   ```

4. **Monitor events**:
   - Check notification service console output
   - Check RabbitMQ Management UI
   - Check `notifications.log` file

## Development

### Local Development

For local development of individual services:

1. **Build specific service**:
   ```bash
   cd auth-service  # or any other service
   mvn spring-boot:run
   ```

2. **Start dependencies**:
   ```bash
   docker-compose up -d rabbitmq postgres-auth postgres-order postgres-payment postgres-inventory postgres-shipping
   ```

### Running Tests

1. **Unit tests for each service**:
   ```bash
   cd auth-service
   mvn test
   ```

2. **Integration tests**:
   ```bash
   cd integration-tests
   mvn test
   ```

## Architecture Details

### Onion Implementation

Each service follows strict Onion Architecture:

```
src/main/java/com/ecommerce/<service>/
├── domain/           # Pure business logic
│   ├── model/       # Domain entities
│   ├── event/       # Domain events
│   └── repository/  # Repository interfaces
├── application/      # Use cases and orchestration
│   ├── dto/         # Data transfer objects
│   ├── usecase/     # Business use cases
│   └── service/     # Application services
├── infrastructure/   # External integrations
│   ├── config/       # Configuration
│   ├── persistence/  # Database implementations
│   ├── messaging/    # RabbitMQ producers/consumers
│   └── security/    # Security implementations
└── presentation/     # REST controllers
    └── controller/
```

### Key Architecture Rules

- **Domain Layer**: No Spring annotations, no framework dependencies
- **Application Layer**: Use cases and orchestration logic
- **Infrastructure Layer**: JPA, RabbitMQ, security implementations
- **Presentation Layer**: REST controllers only, no business logic

## Monitoring

### Health Endpoints
All services expose `/actuator/health` for health checks.

### Swagger Documentation
All services expose Swagger UI at `/swagger-ui.html`.

### RabbitMQ Management
Access RabbitMQ Management UI at http://localhost:15672

## Troubleshooting

### Common Issues

1. **Port conflicts**: Ensure ports 8081-8086, 5672, 15672, 5432-5436 are available
2. **Database connection issues**: Check PostgreSQL containers are running
3. **RabbitMQ connection issues**: Verify RabbitMQ container is healthy
4. **Build failures**: Ensure Java 21 and Maven 3.8+ are installed

### Logs

- Application logs: Check Docker container logs
- RabbitMQ logs: `docker logs ecommerce-rabbitmq`
- Database logs: `docker logs postgres-*`
- Notifications: Check `notifications.log` file

## Production Considerations

- Configure proper database passwords
- Set up SSL/TLS for all services
- Configure proper RabbitMQ clustering
- Set up monitoring and alerting
- Configure proper logging levels
- Set up backup strategies for databases

## License

This project is for educational purposes to demonstrate event-driven microservices architecture.
