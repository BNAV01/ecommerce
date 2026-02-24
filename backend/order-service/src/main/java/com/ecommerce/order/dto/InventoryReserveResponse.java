package com.ecommerce.order.dto;

public record InventoryReserveResponse(
        boolean reserved,
        String reason
) {
}
