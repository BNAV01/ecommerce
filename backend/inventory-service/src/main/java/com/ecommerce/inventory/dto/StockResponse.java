package com.ecommerce.inventory.dto;

import java.time.Instant;

public record StockResponse(
        String sku,
        long availableQuantity,
        long reservedQuantity,
        Instant updatedAt
) {
}
