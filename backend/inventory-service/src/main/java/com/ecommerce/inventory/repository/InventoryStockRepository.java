package com.ecommerce.inventory.repository;

import com.ecommerce.inventory.domain.InventoryStock;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryStockRepository extends JpaRepository<InventoryStock, UUID> {

    Optional<InventoryStock> findBySku(String sku);
}
