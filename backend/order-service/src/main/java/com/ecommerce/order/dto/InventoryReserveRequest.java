package com.ecommerce.order.dto;

import java.util.List;
import java.util.UUID;

public record InventoryReserveRequest(
        UUID orderId,
        List<InventoryReserveItem> items
) {
    public record InventoryReserveItem(String sku, int quantity) {
    }
}
