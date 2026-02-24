package com.ecommerce.product.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String sku,
        String name,
        String description,
        BigDecimal price,
        String currency,
        boolean active,
        Instant createdAt,
        Instant updatedAt
) {
}
