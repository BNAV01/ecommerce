package com.ecommerce.order.service;

import com.ecommerce.common.correlation.CorrelationId;
import com.ecommerce.order.domain.CustomerOrder;
import com.ecommerce.order.domain.OrderStatus;
import com.ecommerce.order.dto.CreateOrderRequest;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.exception.OrderNotFoundException;
import com.ecommerce.order.mapper.OrderMapper;
import com.ecommerce.order.repository.CustomerOrderRepository;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final OrderMapper orderMapper;
    private final InventoryClient inventoryClient;
    private final OutboxService outboxService;
    private final String orderCreatedTopic;
    private final String orderConfirmedTopic;
    private final String orderCancelledTopic;

    public OrderService(CustomerOrderRepository customerOrderRepository,
                        OrderMapper orderMapper,
                        InventoryClient inventoryClient,
                        OutboxService outboxService,
                        @Value("${app.kafka.topics.order-created:order.created}") String orderCreatedTopic,
                        @Value("${app.kafka.topics.order-confirmed:order.confirmed}") String orderConfirmedTopic,
                        @Value("${app.kafka.topics.order-cancelled:order.cancelled}") String orderCancelledTopic) {
        this.customerOrderRepository = customerOrderRepository;
        this.orderMapper = orderMapper;
        this.inventoryClient = inventoryClient;
        this.outboxService = outboxService;
        this.orderCreatedTopic = orderCreatedTopic;
        this.orderConfirmedTopic = orderConfirmedTopic;
        this.orderCancelledTopic = orderCancelledTopic;
    }

    @Transactional
    public OrderResponse create(CreateOrderRequest request, String correlationIdHeader) {
        String correlationId = CorrelationId.ensure(correlationIdHeader);

        CustomerOrder order = orderMapper.toEntity(request);
        order = customerOrderRepository.save(order);

        outboxService.enqueue(
                "order",
                order.getId().toString(),
                "order.created",
                orderCreatedTopic,
                order.getId().toString(),
                correlationId,
                payload(order, null)
        );

        boolean reserved = inventoryClient.reserve(order, correlationId);
        if (reserved) {
            order.setStatus(OrderStatus.CONFIRMED);
            outboxService.enqueue(
                    "order",
                    order.getId().toString(),
                    "order.confirmed",
                    orderConfirmedTopic,
                    order.getId().toString(),
                    correlationId,
                    payload(order, null)
            );
        } else {
            order.setStatus(OrderStatus.CANCELLED);
            outboxService.enqueue(
                    "order",
                    order.getId().toString(),
                    "order.cancelled",
                    orderCancelledTopic,
                    order.getId().toString(),
                    correlationId,
                    payload(order, "Inventory reservation rejected or unavailable")
            );
        }

        return orderMapper.toResponse(customerOrderRepository.save(order));
    }

    @Transactional(readOnly = true)
    public OrderResponse getById(UUID id) {
        CustomerOrder order = customerOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id.toString()));
        return orderMapper.toResponse(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> list() {
        return customerOrderRepository.findAll().stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Transactional
    public OrderResponse confirm(UUID id, String correlationIdHeader) {
        String correlationId = CorrelationId.ensure(correlationIdHeader);
        CustomerOrder order = customerOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id.toString()));

        order.setStatus(OrderStatus.CONFIRMED);
        outboxService.enqueue(
                "order",
                order.getId().toString(),
                "order.confirmed",
                orderConfirmedTopic,
                order.getId().toString(),
                correlationId,
                payload(order, null)
        );

        return orderMapper.toResponse(customerOrderRepository.save(order));
    }

    @Transactional
    public OrderResponse cancel(UUID id, String correlationIdHeader, String reason) {
        String correlationId = CorrelationId.ensure(correlationIdHeader);
        CustomerOrder order = customerOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id.toString()));

        order.setStatus(OrderStatus.CANCELLED);
        outboxService.enqueue(
                "order",
                order.getId().toString(),
                "order.cancelled",
                orderCancelledTopic,
                order.getId().toString(),
                correlationId,
                payload(order, reason)
        );

        return orderMapper.toResponse(customerOrderRepository.save(order));
    }

    private Map<String, Object> payload(CustomerOrder order, String reason) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("orderId", order.getId().toString());
        payload.put("customerEmail", order.getCustomerEmail());
        payload.put("status", order.getStatus().name());
        payload.put("totalAmount", order.getTotalAmount());
        payload.put("currency", order.getCurrency());
        payload.put("occurredAt", Instant.now().toString());
        payload.put("items", order.getItems().stream().map(item -> Map.of(
                "sku", item.getSku(),
                "quantity", item.getQuantity(),
                "unitPrice", item.getUnitPrice()
        )).toList());
        if (reason != null && !reason.isBlank()) {
            payload.put("reason", reason);
        }
        return payload;
    }
}
