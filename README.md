# Ecommerce Microservices Template

Reusable production-grade monorepo template for an eCommerce platform.

## Target Architecture
- API Gateway: Spring Cloud Gateway (Reactive/WebFlux) on `9000`
- Security: Keycloak on `8181` (OAuth2/JWT)
- Core services (SQL only / MySQL):
  - `product-service` on `8080`
  - `order-service` on `8081`
  - `inventory-service` on `8082`
  - `notification-service` on `8083`
- Messaging: Kafka on `9092`
- Observability:
  - Prometheus `9090`
  - Loki `3100`
  - Tempo `3110`
  - Grafana `3000`
- Runtime targets:
  - Local: Docker Compose
  - Production: Kubernetes

## Technology Stack
- Java 21
- Spring Boot `3.5.11`
- Spring Cloud Gateway (reactive only)
- Maven multi-module backend
- Spring MVC services with JPA + Flyway + MySQL
- Kafka for async communication
- Angular workspace (shop + admin) with Tailwind + SCSS

## Monorepo Structure
```text
ecommerce/
  backend/
    api-gateway/
    product-service/
    order-service/
    inventory-service/
    notification-service/
    libs/
      common-kernel/
      contracts/
    deploy/
      docker/
      k8s/
  frontend/
    shop/
    admin/
    libs/
      ui/
      api/
  docs/
  .github/workflows/
  scripts/
  README.md
```

## Phase Plan
- Phase 0: bootstrap, conventions, parent builds, env strategy, reusable scripts
- Phase 1: reactive gateway routes + CORS + JWT validation
- Phase 2: product CRUD + Flyway + OpenAPI contract
- Phase 3: inventory model + reserve/release + events + outbox
- Phase 4: order lifecycle + sync inventory call + resilience4j + outbox
- Phase 5: notification Kafka consumer + email dispatch + SQL log
- Phase 6: metrics/logs/traces wiring + Grafana provisioning
- Phase 7: Kubernetes base/overlays + CI/CD pipelines

See [docs/phases.md](docs/phases.md) and [docs/architecture.md](docs/architecture.md).

## Security Model
- Identity provider: Keycloak realm `ecommerce`
- Realm roles:
  - `ROLE_ADMIN`
  - `ROLE_CUSTOMER`
- Clients:
  - `shop-web` (public + PKCE)
  - `admin-web` (public + PKCE)
  - `gateway` (confidential)
- Gateway validates JWT and forwards authenticated traffic to services.
- Services also validate JWT as resource servers.

### Local Keycloak users
- Admin: `admin / admin123`
- Customer: `customer / customer123`

## Event Envelope
All Kafka messages use:
```json
{
  "eventId": "uuid",
  "type": "order.created",
  "occurredAt": "2026-02-24T00:00:00Z",
  "correlationId": "uuid",
  "payload": {}
}
```

## Kafka Topics
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
Implemented in `order-service` and `inventory-service`:
- Save business row + outbox row in one DB transaction
- Scheduled publisher pushes pending outbox rows to Kafka
- Marks event as published

## Backend Service Conventions
Each service follows package layout:
```text
config/
controller/
service/
repository/
domain/
dto/
mapper/
exception/
```

Profiles:
- `application.yml`
- `application-dev.yml`
- `application-prod.yml`

Flyway path:
- `src/main/resources/db/migration`

## Local Development (Docker Compose)
### 1. Prepare env file
```bash
cp backend/deploy/docker/.env.template backend/deploy/docker/.env
```

PowerShell:
```powershell
Copy-Item backend/deploy/docker/.env.template backend/deploy/docker/.env
```

### 2. Start full stack
```bash
./scripts/start-local.sh
```

PowerShell:
```powershell
./scripts/start-local.ps1
```

### 3. Access URLs
- Gateway: `http://localhost:9000`
- Keycloak: `http://localhost:8181`
- Prometheus: `http://localhost:9090`
- Grafana: `http://localhost:3000`
- Mailpit UI: `http://localhost:8025`

### 4. Stop stack
```bash
./scripts/stop-local.sh
```

PowerShell:
```powershell
./scripts/stop-local.ps1
```

## Build Commands
### Backend
```bash
mvn -f backend/pom.xml clean verify
```

### Frontend
```bash
cd frontend
npm install
npm run start:shop
npm run start:admin
```

## OpenAPI/AsyncAPI Contracts
- OpenAPI specs: `backend/libs/contracts/src/main/resources/openapi/`
- AsyncAPI/events: `backend/libs/contracts/src/main/resources/asyncapi/events.yaml`

## Generate Typed Frontend API Clients
From `frontend/`:
```bash
npm run generate:api
```
PowerShell:
```powershell
npm run generate:api:ps
```

## Kubernetes
Base manifests:
- `backend/deploy/k8s/base`

Overlays:
- `backend/deploy/k8s/dev`
- `backend/deploy/k8s/prod`

Apply:
```bash
kubectl apply -k backend/deploy/k8s/dev
kubectl apply -k backend/deploy/k8s/prod
```

## CI/CD Workflows
- `.github/workflows/backend-build.yml`
- `.github/workflows/frontend-build.yml`
- `.github/workflows/docker-image-build.yml`
- `.github/workflows/k8s-deploy.yml`

Includes caching for Maven and npm and a deployment placeholder.

## Gateway-Only Frontend Access
Both Angular apps are configured to call `http://localhost:9000` only.
No frontend app calls services directly.

## Notes
- SQL-only architecture (MySQL). No MongoDB/NoSQL.
- Gateway remains purely reactive (`spring-cloud-gateway` + WebFlux stack, no `spring-boot-starter-web`).
