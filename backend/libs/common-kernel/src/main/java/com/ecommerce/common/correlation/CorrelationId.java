package com.ecommerce.common.correlation;

import java.util.UUID;

public final class CorrelationId {

    public static final String HEADER = "X-Correlation-Id";

    private CorrelationId() {
    }

    public static String ensure(String value) {
        if (value == null || value.isBlank()) {
            return UUID.randomUUID().toString();
        }
        return value;
    }
}