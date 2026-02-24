package com.ecommerce.order.service;

import com.ecommerce.common.correlation.CorrelationId;
import com.ecommerce.order.domain.CustomerOrder;
import com.ecommerce.order.dto.InventoryReserveRequest;
import com.ecommerce.order.dto.InventoryReserveResponse;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import java.util.List;
import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class InventoryClient {

    private final RestClient inventoryRestClient;
    private final CircuitBreaker reserveCircuitBreaker;

    public InventoryClient(RestClient inventoryRestClient, CircuitBreakerRegistry circuitBreakerRegistry) {
        this.inventoryRestClient = inventoryRestClient;
        this.reserveCircuitBreaker = circuitBreakerRegistry.circuitBreaker("inventoryReserve");
    }

    public boolean reserve(CustomerOrder order, String correlationIdHeader) {
        String correlationId = CorrelationId.ensure(correlationIdHeader);
        Supplier<Boolean> reserveSupplier = CircuitBreaker.decorateSupplier(
                reserveCircuitBreaker,
                () -> reserveInternal(order, correlationId)
        );

        try {
            return reserveSupplier.get();
        } catch (Exception exception) {
            return false;
        }
    }

    private boolean reserveInternal(CustomerOrder order, String correlationId) {
        List<InventoryReserveRequest.InventoryReserveItem> items = order.getItems().stream()
                .map(item -> new InventoryReserveRequest.InventoryReserveItem(item.getSku(), item.getQuantity()))
                .toList();

        InventoryReserveRequest request = new InventoryReserveRequest(order.getId(), items);

        InventoryReserveResponse response = inventoryRestClient.post()
                .uri("/api/inventory/reservations/reserve")
                .header(CorrelationId.HEADER, correlationId)
                .body(request)
                .retrieve()
                .body(InventoryReserveResponse.class);

        return response != null && response.reserved();
    }
}
