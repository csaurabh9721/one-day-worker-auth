package com.onedayworker.auth.dto;

import com.onedayworker.auth.entity.Role;

import java.util.UUID;

public final class AuthDtos {

    private AuthDtos() {
    }

    public record RegisterRequest(String email, String phone, String password, String role) {
    }

    public record LoginRequest(String emailOrPhone, String password) {
    }

    public record RefreshRequest(String refreshToken) {
    }

    public record LogoutRequest(String refreshToken) {
    }

    public record LogoutAllRequest(UUID identityId) {
    }

    public record ChangePasswordRequest(UUID identityId, String currentPassword, String newPassword) {
    }

    public record ForgotPasswordRequest(String email, String phone) {
    }

    public record ResetPasswordRequest(String email, String phone, String code, String newPassword) {
    }

    public record OtpSendRequest(String email, String phone) {
    }

    public record OtpVerifyRequest(String email, String phone, String code) {
    }

    public record AdminIdentityRequest(UUID identityId) {
    }

    public record AdminRoleRequest(UUID identityId, String roleName) {
    }

    public record AuthResponse(String accessToken, String refreshToken, IdentityDto identity) {
    }
}
