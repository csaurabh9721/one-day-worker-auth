package com.onedayworker.auth.dto;



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

    public record LogoutAllRequest(Long identityId) {
    }

    public record ChangePasswordRequest(Long identityId, String currentPassword, String newPassword) {
    }

    public record ForgotPasswordRequest(String email, String phone) {
    }

    public record ResetPasswordRequest(String email, String phone, String code, String newPassword) {
    }

    public record OtpSendRequest(String email, String phone) {
    }

    public record OtpVerifyRequest(String email, String phone, String code) {
    }

    public record AdminIdentityRequest(Long identityId) {
    }

    public record AdminRoleRequest(Long identityId, String roleName) {
    }

    public record AuthResponse(String accessToken, String refreshToken, IdentityDto identity) {
    }
}
