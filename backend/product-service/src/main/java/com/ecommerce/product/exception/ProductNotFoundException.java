package com.ecommerce.product.exception;

import com.ecommerce.common.exception.ServiceException;

public class ProductNotFoundException extends ServiceException {

    public ProductNotFoundException(String productId) {
        super("product.not-found", "Product not found: " + productId);
    }
}
