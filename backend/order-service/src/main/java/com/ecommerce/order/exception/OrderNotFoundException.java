package com.ecommerce.order.exception;

import com.ecommerce.common.exception.ServiceException;

public class OrderNotFoundException extends ServiceException {

    public OrderNotFoundException(String id) {
        super("order.not-found", "Order not found: " + id);
    }
}
