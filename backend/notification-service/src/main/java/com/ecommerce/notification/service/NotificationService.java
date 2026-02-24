package com.ecommerce.notification.service;

import com.ecommerce.notification.domain.NotificationLog;
import com.ecommerce.notification.dto.EmailRequest;
import com.ecommerce.notification.dto.NotificationLogResponse;
import com.ecommerce.notification.exception.NotificationDispatchException;
import com.ecommerce.notification.mapper.NotificationMapper;
import com.ecommerce.notification.repository.NotificationLogRepository;
import java.util.List;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final JavaMailSender mailSender;
    private final NotificationLogRepository notificationLogRepository;
    private final NotificationMapper notificationMapper;

    public NotificationService(JavaMailSender mailSender,
                               NotificationLogRepository notificationLogRepository,
                               NotificationMapper notificationMapper) {
        this.mailSender = mailSender;
        this.notificationLogRepository = notificationLogRepository;
        this.notificationMapper = notificationMapper;
    }

    public NotificationLogResponse sendEmail(EmailRequest request) {
        NotificationLog log = new NotificationLog();
        log.setRecipient(request.recipient());
        log.setSubject(request.subject());
        log.setBody(request.body());

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(request.recipient());
            message.setSubject(request.subject());
            message.setText(request.body());
            mailSender.send(message);
            log.setStatus("SENT");
        } catch (Exception exception) {
            log.setStatus("FAILED");
            notificationLogRepository.save(log);
            throw new NotificationDispatchException("Email dispatch failed", exception);
        }

        NotificationLog saved = notificationLogRepository.save(log);
        return notificationMapper.toResponse(saved);
    }

    public List<NotificationLogResponse> latestLogs() {
        return notificationLogRepository.findTop100ByOrderByCreatedAtDesc().stream()
                .map(notificationMapper::toResponse)
                .toList();
    }
}
