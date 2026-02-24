package com.ecommerce.inventory.dto;

import java.util.UUID;

public record ReserveStockResponse(
        UUID orderId,
        boolean reserved,
        String reason
) {
}
