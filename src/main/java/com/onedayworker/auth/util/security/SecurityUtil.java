package com.onedayworker.auth.util.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class SecurityUtil {

    public static UUID getCurrentUserId() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            assert auth != null;
            return (UUID) auth.getPrincipal();
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
}
