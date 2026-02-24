package com.ecommerce.product.service;

import com.ecommerce.common.correlation.CorrelationId;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.dto.ProductCreateRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.dto.ProductUpdateRequest;
import com.ecommerce.product.exception.DuplicateSkuException;
import com.ecommerce.product.exception.ProductNotFoundException;
import com.ecommerce.product.mapper.ProductMapper;
import com.ecommerce.product.repository.ProductRepository;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductEventPublisher productEventPublisher;

    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper,
                          ProductEventPublisher productEventPublisher) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productEventPublisher = productEventPublisher;
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> list(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ProductResponse getById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id.toString()));
        return productMapper.toResponse(product);
    }

    @Transactional
    public ProductResponse create(ProductCreateRequest request, String correlationIdHeader) {
        String correlationId = CorrelationId.ensure(correlationIdHeader);

        if (productRepository.findBySku(request.sku().trim()).isPresent()) {
            throw new DuplicateSkuException(request.sku());
        }

        Product product = productMapper.toNewEntity(request);
        Product saved = productRepository.save(product);
        ProductResponse response = productMapper.toResponse(saved);
        productEventPublisher.publishCreated(response, correlationId);
        return response;
    }

    @Transactional
    public ProductResponse update(UUID id, ProductUpdateRequest request, String correlationIdHeader) {
        String correlationId = CorrelationId.ensure(correlationIdHeader);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id.toString()));

        if (productRepository.existsBySkuAndIdNot(request.sku().trim(), id)) {
            throw new DuplicateSkuException(request.sku());
        }

        productMapper.updateEntity(product, request);
        Product saved = productRepository.save(product);
        ProductResponse response = productMapper.toResponse(saved);
        productEventPublisher.publishUpdated(response, correlationId);
        return response;
    }

    @Transactional
    public void delete(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id.toString()));
        productRepository.delete(product);
    }
}
