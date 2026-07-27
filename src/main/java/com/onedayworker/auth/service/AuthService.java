package com.onedayworker.auth.service;

import com.onedayworker.auth.dto.*;
import com.onedayworker.auth.entity.Identity;
import com.onedayworker.auth.entity.Otp;
import com.onedayworker.auth.entity.RefreshToken;
import com.onedayworker.auth.mapper.IdentityMapper;
import com.onedayworker.auth.repository.IdentityRepository;
import com.onedayworker.auth.repository.OtpRepository;
import com.onedayworker.auth.repository.RefreshTokenRepository;
import com.onedayworker.auth.util.DateTimeUtil;
import com.onedayworker.auth.util.PasswordUtil;
import com.onedayworker.auth.util.ValidationUtil;
import com.onedayworker.auth.exception.UnauthorizedException;
import com.onedayworker.auth.util.enums.AccountStatus;
import com.onedayworker.auth.util.enums.OtpType;
import com.onedayworker.auth.util.enums.RoleType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final IdentityRepository identityRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OtpRepository otpRepository;
    private final IdentityRoleService identityRoleService;
    private final CustomerFeignClient customerFeignClient;


    private final Map<String, UUID> accessTokenStore = new ConcurrentHashMap<>();

    @Transactional
    public RegisterResponse register(RegisterRequestDto request, RoleType roleType) {
        validatePassword(request.getPassword());
        if (!ValidationUtil.isBlank(request.getEmail()) && identityRepository.existsByEmail(request.getEmail().trim().toLowerCase())) {
            throw new IllegalArgumentException("Email already registered");
        }
        if (!ValidationUtil.isBlank(request.getPhone()) && identityRepository.existsByPhone(request.getPhone().trim())) {
            throw new IllegalArgumentException("Phone already registered");
        }

        Identity identity = Identity.builder()
                .email(!ValidationUtil.isBlank(request.getEmail()) ? request.getEmail().trim().toLowerCase() : null)
                .phone(!ValidationUtil.isBlank(request.getPhone()) ? request.getPhone().trim() : null)
                .password(PasswordUtil.hash(request.getPassword()))
                .status(AccountStatus.ACTIVE)
                .emailVerified(false)
                .phoneVerified(false)
                .failedLoginAttempts(0)
                .build();

        identity = identityRepository.save(identity);
        identityRoleService.assignRole(
                identity.getId(),
                roleType.name()
        );
        CustomerRegistrationRequest customerRegistrationRequest = CustomerRegistrationRequest.builder()
                .identityId(identity.getId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .gender(request.getGender())
                .dob(request.getDob())
                .build();
        CustomerRegistrationResponseDto customerRegistrationResponseDto = customerFeignClient.createCustomer(customerRegistrationRequest);
        return RegisterResponse.builder()
                .uuid(identity.getId())
                .name(customerRegistrationResponseDto.getFirstName() + " " + customerRegistrationResponseDto.getLastName())
                .email(identity.getEmail())
                .phone(identity.getPhone())
                .role(roleType.name())
                .status(identity.getStatus().name())
                .message("User registered successfully")
                .build();
    }

    @Transactional
    public AuthDtos.AuthResponse login(AuthDtos.LoginRequest request) {
        Identity identity = resolveIdentity(request.emailOrPhone());
        if (!PasswordUtil.matches(request.password(), identity.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }
        if (identity.getStatus() == AccountStatus.BLOCKED || identity.getStatus() == AccountStatus.DELETED) {
            throw new UnauthorizedException("Account is not active");
        }
        return issueTokens(identity);
    }

    @Transactional
    public AuthDtos.AuthResponse refresh(AuthDtos.RefreshRequest request) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.refreshToken())
                .orElseThrow(() -> new UnauthorizedException("Invalid refresh token"));

        if (Boolean.TRUE.equals(refreshToken.getRevoked())) {
            throw new UnauthorizedException("Refresh token revoked");
        }
        if (refreshToken.getExpiresAt() != null && refreshToken.getExpiresAt().isBefore(DateTimeUtil.now())) {
            throw new UnauthorizedException("Refresh token expired");
        }

        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);

        Identity identity = refreshToken.getIdentity();
        return issueTokens(identity);
    }

    @Transactional
    public void logout(AuthDtos.LogoutRequest request) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.refreshToken())
                .orElseThrow(() -> new IllegalArgumentException("Refresh token not found"));
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public void logoutAll(AuthDtos.LogoutAllRequest request) {
        Identity identity = identityRepository.findById(request.identityId())
                .orElseThrow(() -> new IllegalArgumentException("Identity not found"));
        for (RefreshToken token : refreshTokenRepository.findAllByIdentityAndRevokedFalse(identity)) {
            token.setRevoked(true);
            refreshTokenRepository.save(token);
        }
        accessTokenStore.entrySet().removeIf(entry -> entry.getValue().equals(identity.getId()));
    }

    @Transactional
    public void changePassword(AuthDtos.ChangePasswordRequest request) {
        Identity identity = identityRepository.findById(request.identityId())
                .orElseThrow(() -> new IllegalArgumentException("Identity not found"));
        if (!PasswordUtil.matches(request.currentPassword(), identity.getPassword())) {
            throw new UnauthorizedException("Current password is invalid");
        }
        validatePassword(request.newPassword());
        identity.setPassword(PasswordUtil.hash(request.newPassword()));
        identityRepository.save(identity);
        for (RefreshToken token : refreshTokenRepository.findAllByIdentityAndRevokedFalse(identity)) {
            token.setRevoked(true);
            refreshTokenRepository.save(token);
        }
        accessTokenStore.entrySet().removeIf(entry -> entry.getValue().equals(identity.getId()));
    }

    @Transactional
    public void forgotPassword(AuthDtos.ForgotPasswordRequest request) {
        Identity identity = resolveIdentity(request.email(), request.phone());
        String code = generateOtpCode();
        Otp otp = Otp.builder()
                .identity(identity)
                .code(code)
                .type(OtpType.PASSWORD_RESET)
                .attempts(0)
                .used(false)
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .build();
        otpRepository.save(otp);
    }

    @Transactional
    public void resetPassword(AuthDtos.ResetPasswordRequest request) {
        Identity identity = resolveIdentity(request.email(), request.phone());
        Otp otp = otpRepository.findByCodeAndTypeAndUsedFalse(request.code(), OtpType.PASSWORD_RESET)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reset code"));
        if (!otp.getIdentity().getId().equals(identity.getId())) {
            throw new IllegalArgumentException("Reset code does not match identity");
        }
        if (otp.getExpiresAt() != null && otp.getExpiresAt().isBefore(DateTimeUtil.now())) {
            throw new IllegalArgumentException("Reset code expired");
        }

        validatePassword(request.newPassword());
        identity.setPassword(PasswordUtil.hash(request.newPassword()));
        identityRepository.save(identity);
        otp.setUsed(true);
        otpRepository.save(otp);
    }

    public IdentityDto me(HttpServletRequest request, String authorizationHeader) {
        String accessToken = extractAccessToken(request, authorizationHeader);
        UUID identityId = accessTokenStore.get(accessToken);
        if (identityId == null) {
            throw new UnauthorizedException("Invalid or missing access token");
        }
        Identity identity = identityRepository.findById(identityId)
                .orElseThrow(() -> new IllegalArgumentException("Identity not found"));
        return IdentityMapper.toDto(identity);
    }

    private AuthDtos.AuthResponse issueTokens(Identity identity) {
        String accessToken = generateToken();
        String refreshTokenValue = generateToken();
        accessTokenStore.put(accessToken, identity.getId());
        RefreshToken refreshToken = RefreshToken.builder()
                .identity(identity)
                .token(refreshTokenValue)
                .expiresAt(DateTimeUtil.now().plusDays(30))
                .revoked(false)
                .build();
        refreshTokenRepository.save(refreshToken);
        return new AuthDtos.AuthResponse(accessToken, refreshTokenValue, IdentityMapper.toDto(identity));
    }


    private Identity resolveIdentity(String emailOrPhone) {
        if (ValidationUtil.isBlank(emailOrPhone)) {
            throw new IllegalArgumentException("Email or phone is required");
        }
        String value = emailOrPhone.trim();
        if (value.contains("@")) {
            return identityRepository.findByEmail(value.toLowerCase())
                    .orElseThrow(() -> new IllegalArgumentException("Identity not found"));
        }
        return identityRepository.findByPhone(value)
                .orElseThrow(() -> new IllegalArgumentException("Identity not found"));
    }

    private Identity resolveIdentity(String email, String phone) {
        if (!ValidationUtil.isBlank(email)) {
            return identityRepository.findByEmail(email.trim().toLowerCase())
                    .orElseThrow(() -> new IllegalArgumentException("Identity not found"));
        }
        if (!ValidationUtil.isBlank(phone)) {
            return identityRepository.findByPhone(phone.trim())
                    .orElseThrow(() -> new IllegalArgumentException("Identity not found"));
        }
        throw new IllegalArgumentException("Email or phone is required");
    }

    private String extractAccessToken(HttpServletRequest request, String authorizationHeader) {
        if (!ValidationUtil.isBlank(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7).trim();
        }
        if (request != null) {
            String header = request.getHeader("Authorization");
            if (!ValidationUtil.isBlank(header) && header.startsWith("Bearer ")) {
                return header.substring(7).trim();
            }
        }
        throw new UnauthorizedException("Missing access token");
    }

    private void validatePassword(String password) {
        if (ValidationUtil.isBlank(password) || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private String generateOtpCode() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }
}
