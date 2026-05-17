# Event-Driven System (Spring Boot + RabbitMQ + Onion Architecture)

Six independent Spring Boot microservices communicating via a RabbitMQ topic
exchange (`app.exchange`). Each service follows Onion Architecture:

```
domain/         -> pure business logic & entities (no framework)
application/    -> use cases / services / ports
infrastructure/ -> DB (JPA), RabbitMQ adapters, JWT, config
presentation/   -> REST controllers, DTOs
```

## Services
| Service        | Port | Publishes                          | Listens to                                   |
|----------------|------|------------------------------------|----------------------------------------------|
| auth-service   | 8081 | user.registered                    | -                                            |
| order-service  | 8082 | order.created                      | -                                            |
| payment-service| 8083 | payment.completed / payment.failed | order.created                                |
| inventory-svc  | 8084 | stock.reserved / stock.failed      | order.created                                |
| shipping-svc   | 8085 | shipment.created                   | payment.completed, stock.reserved            |
| notification   | 8086 | -                                  | # (all events)                               |

## Run
```bash
docker compose up --build
```
RabbitMQ UI: http://localhost:15672  (guest / guest)

## Quick test
1. Register user:  `POST http://localhost:8081/api/auth/register {"email":"a@b.com","password":"x"}`
2. Login:          `POST http://localhost:8081/api/auth/login`  -> returns JWT
3. Create order:   `POST http://localhost:8082/api/orders` with `Authorization: Bearer <jwt>`
4. Watch notification-service logs for the full event chain.

## Bonus included
- JWT validation filter (auth + order services)
- RabbitMQ DLQ (`app.exchange.dlq`) with retry
- Docker Compose for the full system
- Swagger UI on each service: `/swagger-ui.html`
