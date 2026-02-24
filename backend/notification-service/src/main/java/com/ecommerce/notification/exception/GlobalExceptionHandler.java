package com.ecommerce.notification.exception;

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

    @ExceptionHandler(NotificationDispatchException.class)
    public ResponseEntity<ProblemDetail> handleDispatch(NotificationDispatchException exception, HttpServletRequest request) {
        String correlationId = CorrelationId.ensure(request.getHeader(CorrelationId.HEADER));
        ProblemDetail detail = ProblemDetailsFactory.of(
                HttpStatus.BAD_GATEWAY,
                "Notification dispatch failed",
                exception.getMessage(),
                exception.getCode(),
                correlationId
        );
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(detail);
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
}
