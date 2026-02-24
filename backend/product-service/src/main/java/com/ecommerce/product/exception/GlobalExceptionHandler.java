package com.ecommerce.product.exception;

import com.ecommerce.common.correlation.CorrelationId;
import com.ecommerce.common.exception.ProblemDetailsFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFound(ProductNotFoundException exception, HttpServletRequest request) {
        String correlationId = CorrelationId.ensure(request.getHeader(CorrelationId.HEADER));
        ProblemDetail detail = ProblemDetailsFactory.of(
                HttpStatus.NOT_FOUND,
                "Product not found",
                exception.getMessage(),
                exception.getCode(),
                correlationId
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detail);
    }

    @ExceptionHandler(DuplicateSkuException.class)
    public ResponseEntity<ProblemDetail> handleDuplicate(DuplicateSkuException exception, HttpServletRequest request) {
        String correlationId = CorrelationId.ensure(request.getHeader(CorrelationId.HEADER));
        ProblemDetail detail = ProblemDetailsFactory.of(
                HttpStatus.CONFLICT,
                "Duplicate SKU",
                exception.getMessage(),
                exception.getCode(),
                correlationId
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(detail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException exception, HttpServletRequest request) {
        String correlationId = CorrelationId.ensure(request.getHeader(CorrelationId.HEADER));
        ProblemDetail detail = ProblemDetailsFactory.of(
                HttpStatus.BAD_REQUEST,
                "Validation failed",
                exception.getBindingResult().toString(),
                "validation.failed",
                correlationId
        );
        return ResponseEntity.badRequest().body(detail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleUnexpected(Exception exception, HttpServletRequest request) {
        String correlationId = CorrelationId.ensure(request.getHeader(CorrelationId.HEADER));
        ProblemDetail detail = ProblemDetailsFactory.of(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal server error",
                exception.getMessage(),
                "internal.error",
                correlationId
        );
        return ResponseEntity.internalServerError().body(detail);
    }
}
