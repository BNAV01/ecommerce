package com.ecommerce.common.exception;

import java.net.URI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public final class ProblemDetailsFactory {

    private ProblemDetailsFactory() {
    }

    public static ProblemDetail of(HttpStatus status, String title, String detail, String code, String correlationId) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detail);
        problemDetail.setTitle(title);
        problemDetail.setType(URI.create("https://errors.ecommerce.local/" + code));
        problemDetail.setProperty("code", code);
        problemDetail.setProperty("correlationId", correlationId);
        return problemDetail;
    }
}