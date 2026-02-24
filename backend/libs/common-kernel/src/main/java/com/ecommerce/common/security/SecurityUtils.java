package com.ecommerce.common.security;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static Set<String> authorities(Authentication authentication) {
        if (authentication == null || authentication.getAuthorities() == null) {
            return Set.of();
        }
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
    }

    public static boolean hasAnyRole(Authentication authentication, String... roles) {
        Set<String> existing = authorities(authentication);
        for (String role : roles) {
            if (existing.contains(role)) {
                return true;
            }
        }
        return false;
    }
}