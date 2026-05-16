# Event-Driven E-Commerce System

A distributed microservices system built with **Spring Boot**, **RabbitMQ**, and **Onion Architecture**.

## Architecture

```
User → Auth Service → [user.registered] → Notification
User → Order Service → [order.created] → Payment Service ──► [payment.completed/failed]
                                       → Inventory Service ► [stock.reserved/failed]
                                                                     ↓ (both success)
                                                              Shipping Service → [shipment.created]
                                                                     ↓
                                                              Notification Service (listens to ALL)
```

## Services

| Service | Port | Description |
|---------|------|-------------|
| Auth | 8081 | Registration, Login (JWT), publishes `user.registered` |
| Order | 8082 | Creates orders, publishes `order.created` |
| Payment | 8083 | Listens to `order.created`, mock payment, publishes result |
| Inventory | 8084 | Listens to `order.created`, checks stock, publishes result |
| Shipping | 8085 | Listens to payment + stock success, creates shipment |
| Notification | 8086 | Listens to ALL events, logs notifications |

## RabbitMQ

- **Exchange**: `app.exchange` (Topic Exchange)
- **Routing Keys**: `user.registered`, `order.created`, `payment.completed`, `payment.failed`, `stock.reserved`, `stock.failed`, `shipment.created`

## Onion Architecture (per service)

```
presentation/   ← REST Controllers, DTOs
application/    ← Use Cases, Service Classes, Port Interfaces
domain/         ← Entities, Domain Events, Repository Interfaces (pure Java)
infrastructure/ ← JPA Repos, RabbitMQ Publishers/Consumers, Configs
```

## Quick Start

### Prerequisites
- Docker & Docker Compose
- Java 17 + Maven (for local dev)

### Run with Docker
```bash
docker-compose up --build
```

### Run locally (each in separate terminal)
```bash
# Start RabbitMQ first
docker run -d -p 5672:5672 -p 15672:15672 rabbitmq:3-management

# Then start each service
cd auth-service && mvn spring-boot:run
cd order-service && mvn spring-boot:run
# ... etc
```

## Swagger UI

Each service exposes Swagger at `http://localhost:{port}/swagger-ui.html`

## Typical Flow

1. **Register**: `POST http://localhost:8081/api/auth/register`
2. **Login**: `POST http://localhost:8081/api/auth/login` → get JWT token
3. **Create Order**: `POST http://localhost:8082/api/orders` (include `Authorization: Bearer <token>`)
4. Watch payment, inventory, shipping, and notification services react via RabbitMQ!

## RabbitMQ Management UI

Open `http://localhost:15672` (guest/guest) to monitor queues and messages.
