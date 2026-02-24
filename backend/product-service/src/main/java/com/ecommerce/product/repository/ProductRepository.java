package com.ecommerce.product.repository;

import com.ecommerce.product.domain.Product;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    Optional<Product> findBySku(String sku);

    boolean existsBySkuAndIdNot(String sku, UUID id);
}
