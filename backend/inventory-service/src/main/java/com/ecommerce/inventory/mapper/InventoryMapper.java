package com.ecommerce.inventory.mapper;

import com.ecommerce.inventory.domain.InventoryStock;
import com.ecommerce.inventory.dto.StockResponse;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {

    public StockResponse toResponse(InventoryStock stock) {
        return new StockResponse(
                stock.getSku(),
                stock.getAvailableQuantity(),
                stock.getReservedQuantity(),
                stock.getUpdatedAt()
        );
    }
}
