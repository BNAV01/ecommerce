package com.ecommerce.notification.controller;

import com.ecommerce.notification.dto.EmailRequest;
import com.ecommerce.notification.dto.NotificationLogResponse;
import com.ecommerce.notification.service.NotificationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/test-email")
    public ResponseEntity<NotificationLogResponse> sendTestEmail(@Valid @RequestBody EmailRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(notificationService.sendEmail(request));
    }

    @GetMapping("/logs")
    public List<NotificationLogResponse> logs() {
        return notificationService.latestLogs();
    }
}
