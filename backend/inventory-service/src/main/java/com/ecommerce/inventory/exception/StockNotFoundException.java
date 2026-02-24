package com.ecommerce.inventory.exception;

import com.ecommerce.common.exception.ServiceException;

public class StockNotFoundException extends ServiceException {

    public StockNotFoundException(String sku) {
        super("inventory.stock-not-found", "Stock not found for sku: " + sku);
    }
}
