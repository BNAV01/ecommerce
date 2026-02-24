package com.ecommerce.notification.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmailRequest(
        @NotBlank @Email @Size(max = 255) String recipient,
        @NotBlank @Size(max = 255) String subject,
        @NotBlank String body
) {
}
