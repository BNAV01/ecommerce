package com.ecommerce.product.mapper;

import com.ecommerce.product.domain.Product;
import com.ecommerce.product.dto.ProductCreateRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.dto.ProductUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toNewEntity(ProductCreateRequest request) {
        Product entity = new Product();
        entity.setSku(request.sku().trim());
        entity.setName(request.name().trim());
        entity.setDescription(request.description().trim());
        entity.setPrice(request.price());
        entity.setCurrency(request.currency().trim().toUpperCase());
        entity.setActive(request.active());
        return entity;
    }

    public void updateEntity(Product entity, ProductUpdateRequest request) {
        entity.setSku(request.sku().trim());
        entity.setName(request.name().trim());
        entity.setDescription(request.description().trim());
        entity.setPrice(request.price());
        entity.setCurrency(request.currency().trim().toUpperCase());
        entity.setActive(request.active());
    }

    public ProductResponse toResponse(Product entity) {
        return new ProductResponse(
                entity.getId(),
                entity.getSku(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getCurrency(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
