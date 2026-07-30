package com.onedayworker.auth.controller;

import com.onedayworker.auth.dto.AuthDtos;
import com.onedayworker.auth.dto.IdentityDto;
import com.onedayworker.auth.dto.RegisterRequestDto;
import com.onedayworker.auth.dto.RegisterResponse;
import com.onedayworker.auth.service.AuthService;
import com.onedayworker.auth.util.APIBaseRoute;
import com.onedayworker.auth.util.enums.RoleType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIBaseRoute.API_VERSION +"/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registerCustomer")
    public ResponseEntity<RegisterResponse> registerCustomer(@RequestBody RegisterRequestDto request) {
        return ResponseEntity.ok(authService.register(request, RoleType.CUSTOMER));
    }
    @PostMapping("/registerWorker")
    public ResponseEntity<RegisterResponse> registerWorker(@RequestBody RegisterRequestDto request) {
        return ResponseEntity.ok(authService.register(request, RoleType.WORKER));
    }

    @PostMapping("/login")
    public ResponseEntity<RegisterResponse> login(@RequestBody AuthDtos.LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RegisterResponse> refresh(@RequestBody AuthDtos.RefreshRequest request) {
        return ResponseEntity.ok(authService.refresh(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody AuthDtos.LogoutRequest request) {
        authService.logout(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logout-all")
    public ResponseEntity<Void> logoutAll(@RequestBody AuthDtos.LogoutAllRequest request) {
        authService.logoutAll(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody AuthDtos.ChangePasswordRequest request) {
        authService.changePassword(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody AuthDtos.ForgotPasswordRequest request) {
        authService.forgotPassword(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody AuthDtos.ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<IdentityDto> me() {
        return ResponseEntity.ok(authService.me());
    }
}
