# School Fee Payment System — Distributed Microservices

A distributed event-driven microservices system built with **Spring Boot**, **RabbitMQ**, and **Onion Architecture**.

---

## Architecture Overview

```
┌─────────────┐     REST      ┌──────────────┐
│   Client    │ ────────────► │ Auth Service │ :8081
└─────────────┘               └──────┬───────┘
                                     │ user.registered
                                     ▼
                              ┌──────────────┐
              REST             │Order Service │ :8082
Client ──────────────────────► │              │
                              └──────┬───────┘
                                     │ order.created
                          ┌──────────┴──────────┐
                          ▼                     ▼
                 ┌────────────────┐   ┌──────────────────┐
                 │Payment Service │   │Inventory Service  │
                 │    :8083       │   │     :8084         │
                 └───────┬────────┘   └────────┬──────────┘
                payment.completed        stock.reserved
                payment.failed           stock.failed
                         │                     │
                         └──────────┬──────────┘
                                    ▼
                          ┌─────────────────┐
                          │Shipping Service │ :8085
                          └────────┬────────┘
                              shipment.created
                                   │
                          ┌────────▼────────┐
                          │  Notification   │ :8086
                          │   Service       │ (listens ALL)
                          └─────────────────┘
```

---

## Services

| Service       | Port | Description                                 |
|---------------|------|---------------------------------------------|
| auth-service       | 8081 | Register & Login (JWT)                 |
| order-service      | 8082 | Create fee orders                      |
| payment-service    | 8083 | Processes payments (event-driven)      |
| inventory-service  | 8084 | Checks fee availability (event-driven) |
| shipping-service   | 8085 | Creates shipments (event-driven)       |
| notification-service| 8086| Logs all events to console            |

---

## Prerequisites

1. **Java 17+** installed
2. **Maven 3.8+** installed
3. **RabbitMQ** running locally on default port `5672`

### Install RabbitMQ (Windows)
```
# Option 1: Chocolatey
choco install rabbitmq

# Option 2: Download installer from
# https://www.rabbitmq.com/install-windows.html
# Then start via: rabbitmq-service start

# Enable management UI (optional)
rabbitmq-plugins enable rabbitmq_management
# Access at: http://localhost:15672  (guest/guest)
```

---

## Running All Services

Open **6 separate terminals** in the `backend` folder, one per service:

```bash
# Terminal 1 - Auth Service
cd auth-service
mvn spring-boot:run

# Terminal 2 - Order Service
cd order-service
mvn spring-boot:run

# Terminal 3 - Payment Service
cd payment-service
mvn spring-boot:run

# Terminal 4 - Inventory Service
cd inventory-service
mvn spring-boot:run

# Terminal 5 - Shipping Service
cd shipping-service
mvn spring-boot:run

# Terminal 6 - Notification Service
cd notification-service
mvn spring-boot:run
```

---

## API Testing Flow

### Step 1 — Register a User
```http
POST http://localhost:8081/api/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@school.com",
  "password": "password123"
}
```
**Response:** JWT token + triggers `user.registered` event

---

### Step 2 — Login
```http
POST http://localhost:8081/api/auth/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "password123"
}
```
**Response:** JWT token (use in Authorization header for protected routes)

---

### Step 3 — Create a Fee Order
```http
POST http://localhost:8082/api/orders
Content-Type: application/json

{
  "studentId": "STU-001",
  "studentName": "John Doe",
  "feeType": "TUITION",
  "amount": 1500.00
}
```
**Response:** Order created → triggers `order.created` event

**Watch Notification Service console** — you will see:
```
=== NOTIFICATION === [ORDER CREATED] OrderId: 1 | Student: STU-001 ...
=== NOTIFICATION === [PAYMENT SUCCESS] OrderId: 1 | Ref: PAY-XXXXXXXX
=== NOTIFICATION === [STOCK RESERVED] OrderId: 1 | FeeType: TUITION
=== NOTIFICATION === [SHIPMENT CREATED] OrderId: 1 | Tracking: TRK-XXXXXXXX
```

---

## Swagger UI (per service)

| Service             | Swagger URL                                    |
|---------------------|------------------------------------------------|
| Auth Service        | http://localhost:8081/swagger-ui.html          |
| Order Service       | http://localhost:8082/swagger-ui.html          |
| Payment Service     | http://localhost:8083/swagger-ui.html          |
| Inventory Service   | http://localhost:8084/swagger-ui.html          |
| Shipping Service    | http://localhost:8085/swagger-ui.html          |
| Notification Service| http://localhost:8086/swagger-ui.html          |

---

## H2 Database Consoles

| Service          | H2 Console URL                          | JDBC URL              |
|------------------|-----------------------------------------|-----------------------|
| Auth Service     | http://localhost:8081/h2-console        | jdbc:h2:mem:authdb    |
| Order Service    | http://localhost:8082/h2-console        | jdbc:h2:mem:orderdb   |
| Payment Service  | http://localhost:8083/h2-console        | jdbc:h2:mem:paymentdb |
| Inventory Service| http://localhost:8084/h2-console        | jdbc:h2:mem:inventorydb|
| Shipping Service | http://localhost:8085/h2-console        | jdbc:h2:mem:shippingdb|

Username: `sa` | Password: `password`

---

## RabbitMQ Event Flow

```
Exchange: app.exchange (Topic)

Routing Keys:
  user.registered    → auth-service publishes
  order.created      → order-service publishes
  payment.completed  → payment-service publishes
  payment.failed     → payment-service publishes
  stock.reserved     → inventory-service publishes
  stock.failed       → inventory-service publishes
  shipment.created   → shipping-service publishes

Queues:
  auth.queue                        ← user.registered
  order.queue                       ← order.created
  payment.queue                     ← order.created  (+ DLQ)
  inventory.queue                   ← order.created  (+ DLQ)
  shipping.payment.queue            ← payment.completed
  shipping.stock.queue              ← stock.reserved
  notification.user.queue           ← user.registered
  notification.order.queue          ← order.created
  notification.payment.completed.queue ← payment.completed
  notification.payment.failed.queue    ← payment.failed
  notification.stock.reserved.queue    ← stock.reserved
  notification.stock.failed.queue      ← stock.failed
  notification.shipment.queue          ← shipment.created
```

---

## Onion Architecture (per service)

```
src/main/java/com/school/{service}/
├── domain/
│   ├── entity/       ← Pure Java business objects (no annotations)
│   └── event/        ← Domain events (plain Java)
├── application/
│   ├── port/         ← Interfaces (Repository + EventPublisher)
│   └── service/      ← Use cases / business logic
├── infrastructure/
│   ├── config/       ← RabbitMQ + Swagger config
│   ├── messaging/    ← Listeners + Publishers
│   ├── persistence/  ← JPA entities + repository implementations
│   └── security/     ← JWT filter + SecurityConfig (auth-service only)
└── presentation/
    ├── controller/   ← REST controllers
    └── dto/          ← Request/Response DTOs
```

---

## Bonus Features Included

- JWT Authentication with filter
- Dead Letter Queues (DLQ) on payment + inventory
- Retry mechanism (3 attempts with backoff) on payment + inventory
- Swagger/OpenAPI on all services
- H2 in-memory DB per service (isolated)
- Parallel processing: Payment + Inventory consume `order.created` simultaneously
