package com.ecommerce.inventory.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

public record ReleaseStockRequest(
        @NotNull UUID orderId,
        @Valid @NotEmpty List<ReleaseItem> items
) {
    public record ReleaseItem(@NotBlank @Size(max = 64) String sku, @Min(1) int quantity) {
    }
}
