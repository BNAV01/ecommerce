package com.ecommerce.inventory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StockAdjustmentRequest(
        @NotBlank @Size(max = 64) String sku,
        long delta
) {
}
