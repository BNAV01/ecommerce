package com.ecommerce.common.exception;

import java.net.URI;
import java.time.Instant;

public record ApiError(
        String code,
        String message,
        int status,
        URI type,
        Instant timestamp,
        String correlationId
) {
    public static ApiError of(String code, String message, int status, URI type, String correlationId) {
        return new ApiError(code, message, status, type, Instant.now(), correlationId);
    }
}