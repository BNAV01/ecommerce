# Delivery Phases

## Phase 0
- Bootstrap monorepo structure
- Standardize naming, package structure, env conventions
- Configure Maven multi-module backend and Angular workspace
- Add reusable scripts, docker assets, and CI pipeline skeletons

## Phase 1
- Configure reactive gateway routes and CORS
- Integrate JWT validation against Keycloak issuer
- Enforce role-based access rules at gateway edge

## Phase 2
- Implement product-service CRUD
- Flyway migrations + OpenAPI contract
- Publish product events to Kafka

## Phase 3
- Implement inventory stock model
- Add reserve/release/adjust endpoints
- Emit inventory events with outbox transaction guarantee

## Phase 4
- Implement order lifecycle endpoints
- Sync inventory reservation with resilience4j circuit breaker
- Publish order lifecycle events with outbox

## Phase 5
- Implement notification Kafka consumers
- Add email dispatch and SQL logging
- Support direct admin-triggered test email endpoint

## Phase 6
- Wire Prometheus/Loki/Tempo/Grafana
- Add starter dashboard provisioning
- Expose actuator metrics and health probes

## Phase 7
- Add Kubernetes base manifests and overlays
- Add deployment workflow placeholders
- Harden production config (resources, probes, scaling stubs)
