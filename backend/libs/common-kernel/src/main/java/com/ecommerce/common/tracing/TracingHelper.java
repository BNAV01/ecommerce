package com.ecommerce.common.tracing;

import io.micrometer.tracing.Tracer;
import java.time.OffsetDateTime;

public final class TracingHelper {

    private TracingHelper() {
    }

    public static String currentTraceId(Tracer tracer) {
        if (tracer == null || tracer.currentSpan() == null || tracer.currentSpan().context() == null) {
            return "";
        }
        return tracer.currentSpan().context().traceId();
    }

    public static OffsetDateTime nowUtc() {
        return OffsetDateTime.now();
    }
}