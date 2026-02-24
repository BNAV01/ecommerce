package com.ecommerce.notification.exception;

import com.ecommerce.common.exception.ServiceException;

public class NotificationDispatchException extends ServiceException {

    public NotificationDispatchException(String message, Throwable cause) {
        super("notification.dispatch-failed", message + ": " + cause.getMessage());
    }
}
