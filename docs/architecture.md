# Ecommerce Microservices Architecture

## Layers
- API Gateway: Spring Cloud Gateway (WebFlux) on port 9000
- Security: Keycloak on port 8181 with OAuth2/JWT
- Core Services:
  - product-service (REST + MySQL) on 8080
  - order-service (REST + MySQL + outbox + resilience4j) on 8081
  - inventory-service (REST + MySQL + outbox) on 8082
  - notification-service (Kafka consumer + mail + MySQL log) on 8083
- Messaging: Kafka on 9092
- Observability: Prometheus (9090), Loki (3100), Tempo (3110), Grafana (3000)
- Target runtime: Kubernetes (with Docker Compose for local)

## Event Envelope
```json
{
  "eventId": "uuid",
  "type": "order.created",
  "occurredAt": "2026-02-24T00:00:00Z",
  "correlationId": "uuid",
  "payload": {}
}
```

## Topic Catalog
- `product.created`
- `product.updated`
- `sku.updated`
- `order.created`
- `order.confirmed`
- `order.cancelled`
- `inventory.stock-reserved`
- `inventory.stock-rejected`
- `inventory.stock-released`
- `notification.email-requested`

## Outbox Pattern
- Implemented in `order-service` and `inventory-service`
- Each service writes business state and outbox row in the same DB transaction
- Scheduled publisher reads pending rows and pushes to Kafka
