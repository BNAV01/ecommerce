package com.ecommerce.inventory.service;

import com.ecommerce.common.correlation.CorrelationId;
import com.ecommerce.inventory.domain.InventoryStock;
import com.ecommerce.inventory.dto.ReleaseStockRequest;
import com.ecommerce.inventory.dto.ReserveStockRequest;
import com.ecommerce.inventory.dto.ReserveStockResponse;
import com.ecommerce.inventory.dto.StockAdjustmentRequest;
import com.ecommerce.inventory.dto.StockResponse;
import com.ecommerce.inventory.exception.StockNotFoundException;
import com.ecommerce.inventory.exception.StockOperationException;
import com.ecommerce.inventory.mapper.InventoryMapper;
import com.ecommerce.inventory.repository.InventoryStockRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    private final InventoryStockRepository inventoryStockRepository;
    private final InventoryMapper inventoryMapper;
    private final OutboxService outboxService;
    private final String stockReservedTopic;
    private final String stockRejectedTopic;
    private final String stockReleasedTopic;

    public InventoryService(InventoryStockRepository inventoryStockRepository,
                            InventoryMapper inventoryMapper,
                            OutboxService outboxService,
                            @Value("${app.kafka.topics.stock-reserved:inventory.stock-reserved}") String stockReservedTopic,
                            @Value("${app.kafka.topics.stock-rejected:inventory.stock-rejected}") String stockRejectedTopic,
                            @Value("${app.kafka.topics.stock-released:inventory.stock-released}") String stockReleasedTopic) {
        this.inventoryStockRepository = inventoryStockRepository;
        this.inventoryMapper = inventoryMapper;
        this.outboxService = outboxService;
        this.stockReservedTopic = stockReservedTopic;
        this.stockRejectedTopic = stockRejectedTopic;
        this.stockReleasedTopic = stockReleasedTopic;
    }

    @Transactional(readOnly = true)
    public StockResponse getStock(String sku) {
        InventoryStock stock = inventoryStockRepository.findBySku(sku.trim())
                .orElseThrow(() -> new StockNotFoundException(sku));
        return inventoryMapper.toResponse(stock);
    }

    @Transactional
    public StockResponse adjust(StockAdjustmentRequest request, String correlationIdHeader) {
        String correlationId = CorrelationId.ensure(correlationIdHeader);
        String sku = request.sku().trim();

        InventoryStock stock = inventoryStockRepository.findBySku(sku)
                .orElseGet(() -> {
                    InventoryStock newStock = new InventoryStock();
                    newStock.setSku(sku);
                    newStock.setAvailableQuantity(0);
                    newStock.setReservedQuantity(0);
                    return newStock;
                });

        long nextAvailable = stock.getAvailableQuantity() + request.delta();
        if (nextAvailable < 0) {
            throw new StockOperationException("Cannot adjust stock below zero for sku: " + sku);
        }

        stock.setAvailableQuantity(nextAvailable);
        InventoryStock saved = inventoryStockRepository.save(stock);

        outboxService.enqueue(
                "inventory",
                saved.getSku(),
                "sku.updated",
                "sku.updated",
                saved.getSku(),
                correlationId,
                Map.of(
                        "sku", saved.getSku(),
                        "availableQuantity", saved.getAvailableQuantity(),
                        "reservedQuantity", saved.getReservedQuantity()
                )
        );

        return inventoryMapper.toResponse(saved);
    }

    @Transactional
    public ReserveStockResponse reserve(ReserveStockRequest request, String correlationIdHeader) {
        String correlationId = CorrelationId.ensure(correlationIdHeader);

        for (ReserveStockRequest.ReserveItem item : request.items()) {
            InventoryStock stock = inventoryStockRepository.findBySku(item.sku().trim()).orElse(null);
            if (stock == null || stock.getAvailableQuantity() < item.quantity()) {
                outboxService.enqueue(
                        "inventory",
                        request.orderId().toString(),
                        "inventory.stock-rejected",
                        stockRejectedTopic,
                        request.orderId().toString(),
                        correlationId,
                        payloadForReservation(request.orderId().toString(), request.items(), "Insufficient stock")
                );
                return new ReserveStockResponse(request.orderId(), false, "Insufficient stock");
            }
        }

        for (ReserveStockRequest.ReserveItem item : request.items()) {
            InventoryStock stock = inventoryStockRepository.findBySku(item.sku().trim())
                    .orElseThrow(() -> new StockNotFoundException(item.sku()));
            stock.setAvailableQuantity(stock.getAvailableQuantity() - item.quantity());
            stock.setReservedQuantity(stock.getReservedQuantity() + item.quantity());
            inventoryStockRepository.save(stock);
        }

        outboxService.enqueue(
                "inventory",
                request.orderId().toString(),
                "inventory.stock-reserved",
                stockReservedTopic,
                request.orderId().toString(),
                correlationId,
                payloadForReservation(request.orderId().toString(), request.items(), null)
        );

        return new ReserveStockResponse(request.orderId(), true, null);
    }

    @Transactional
    public ReserveStockResponse release(ReleaseStockRequest request, String correlationIdHeader) {
        String correlationId = CorrelationId.ensure(correlationIdHeader);

        for (ReleaseStockRequest.ReleaseItem item : request.items()) {
            InventoryStock stock = inventoryStockRepository.findBySku(item.sku().trim())
                    .orElseThrow(() -> new StockNotFoundException(item.sku()));

            if (stock.getReservedQuantity() < item.quantity()) {
                throw new StockOperationException("Reserved quantity is too low to release for sku: " + item.sku());
            }

            stock.setReservedQuantity(stock.getReservedQuantity() - item.quantity());
            stock.setAvailableQuantity(stock.getAvailableQuantity() + item.quantity());
            inventoryStockRepository.save(stock);
        }

        outboxService.enqueue(
                "inventory",
                request.orderId().toString(),
                "inventory.stock-released",
                stockReleasedTopic,
                request.orderId().toString(),
                correlationId,
                payloadForRelease(request.orderId().toString(), request.items())
        );

        return new ReserveStockResponse(request.orderId(), true, "released");
    }

    private Map<String, Object> payloadForReservation(String orderId,
                                                      java.util.List<ReserveStockRequest.ReserveItem> items,
                                                      String reason) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("orderId", orderId);
        payload.put("items", items.stream().map(item -> Map.of(
                "sku", item.sku(),
                "quantity", item.quantity()
        )).toList());
        if (reason != null && !reason.isBlank()) {
            payload.put("reason", reason);
        }
        return payload;
    }

    private Map<String, Object> payloadForRelease(String orderId,
                                                  java.util.List<ReleaseStockRequest.ReleaseItem> items) {
        return Map.of(
                "orderId", orderId,
                "items", items.stream().map(item -> Map.of(
                        "sku", item.sku(),
                        "quantity", item.quantity()
                )).toList()
        );
    }
}
