package com.ecommerce.product.exception;

import com.ecommerce.common.exception.ServiceException;

public class DuplicateSkuException extends ServiceException {

    public DuplicateSkuException(String sku) {
        super("product.duplicate-sku", "SKU already exists: " + sku);
    }
}
