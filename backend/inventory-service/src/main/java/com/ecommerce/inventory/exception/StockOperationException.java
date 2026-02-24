package com.ecommerce.inventory.exception;

import com.ecommerce.common.exception.ServiceException;

public class StockOperationException extends ServiceException {

    public StockOperationException(String message) {
        super("inventory.stock-operation-failed", message);
    }
}
