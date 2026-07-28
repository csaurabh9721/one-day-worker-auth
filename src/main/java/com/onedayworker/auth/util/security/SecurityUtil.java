package com.onedayworker.auth.util.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static Long getCurrentUserId() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            assert auth != null;
            return (Long) auth.getPrincipal();
        }catch (Exception e){
            throw new RuntimeException();
        }
    }
}
