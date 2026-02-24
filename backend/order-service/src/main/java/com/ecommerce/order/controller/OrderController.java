package com.ecommerce.order.controller;

import com.ecommerce.common.correlation.CorrelationId;
import com.ecommerce.order.dto.CreateOrderRequest;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(
            @Valid @RequestBody CreateOrderRequest request,
            @RequestHeader(name = CorrelationId.HEADER, required = false) String correlationId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(request, correlationId));
    }

    @GetMapping("/{id}")
    public OrderResponse getById(@PathVariable UUID id) {
        return orderService.getById(id);
    }

    @GetMapping
    public List<OrderResponse> list() {
        return orderService.list();
    }

    @PostMapping("/{id}/confirm")
    public OrderResponse confirm(
            @PathVariable UUID id,
            @RequestHeader(name = CorrelationId.HEADER, required = false) String correlationId
    ) {
        return orderService.confirm(id, correlationId);
    }

    @PostMapping("/{id}/cancel")
    public OrderResponse cancel(
            @PathVariable UUID id,
            @RequestHeader(name = CorrelationId.HEADER, required = false) String correlationId,
            @RequestParam(defaultValue = "Cancelled by user") String reason
    ) {
        return orderService.cancel(id, correlationId, reason);
    }
}
