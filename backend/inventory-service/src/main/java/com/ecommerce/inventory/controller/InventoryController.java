package com.ecommerce.inventory.controller;

import com.ecommerce.common.correlation.CorrelationId;
import com.ecommerce.inventory.dto.ReleaseStockRequest;
import com.ecommerce.inventory.dto.ReserveStockRequest;
import com.ecommerce.inventory.dto.ReserveStockResponse;
import com.ecommerce.inventory.dto.StockAdjustmentRequest;
import com.ecommerce.inventory.dto.StockResponse;
import com.ecommerce.inventory.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/{sku}")
    public StockResponse getStock(@PathVariable String sku) {
        return inventoryService.getStock(sku);
    }

    @PostMapping("/adjustments")
    public StockResponse adjust(
            @Valid @RequestBody StockAdjustmentRequest request,
            @RequestHeader(name = CorrelationId.HEADER, required = false) String correlationId
    ) {
        return inventoryService.adjust(request, correlationId);
    }

    @PostMapping("/reservations/reserve")
    public ReserveStockResponse reserve(
            @Valid @RequestBody ReserveStockRequest request,
            @RequestHeader(name = CorrelationId.HEADER, required = false) String correlationId
    ) {
        return inventoryService.reserve(request, correlationId);
    }

    @PostMapping("/reservations/release")
    public ReserveStockResponse release(
            @Valid @RequestBody ReleaseStockRequest request,
            @RequestHeader(name = CorrelationId.HEADER, required = false) String correlationId
    ) {
        return inventoryService.release(request, correlationId);
    }
}
