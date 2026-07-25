package com.onedayworker.auth.service;

import com.onedayworker.auth.dto.AuthDtos;
import com.onedayworker.auth.dto.OtpDto;
import com.onedayworker.auth.entity.Identity;
import com.onedayworker.auth.entity.Otp;
import com.onedayworker.auth.mapper.OtpMapper;
import com.onedayworker.auth.repository.IdentityRepository;
import com.onedayworker.auth.repository.OtpRepository;
import com.onedayworker.auth.util.DateTimeUtil;
import com.onedayworker.auth.util.ValidationUtil;
import com.onedayworker.auth.util.enums.OtpType;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepository repository;
    private final IdentityRepository identityRepository;





    @Transactional
    public void sendOtp(AuthDtos.OtpSendRequest request) {
        Identity identity = resolveIdentity(request.email(), request.phone());
        Otp otp = Otp.builder()
                .identity(identity)
                .code(generateOtpCode())
                .type(resolveOtpType(request.email(), request.phone()))
                .attempts(0)
                .used(false)
                .expiresAt(DateTimeUtil.now().plusMinutes(10))
                .build();
        repository.save(otp);
    }

    @Transactional
    public void verifyOtp(AuthDtos.OtpVerifyRequest request) {
        Identity identity = resolveIdentity(request.email(), request.phone());
        Otp otp = repository.findByCodeAndTypeAndUsedFalse(request.code(), resolveOtpType(request.email(), request.phone()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid OTP"));
        if (!otp.getIdentity().getId().equals(identity.getId())) {
            throw new IllegalArgumentException("OTP does not match the provided identity");
        }
        if (otp.getExpiresAt() != null && otp.getExpiresAt().isBefore(DateTimeUtil.now())) {
            throw new IllegalArgumentException("OTP expired");
        }
        otp.setAttempts(otp.getAttempts() == null ? 1 : otp.getAttempts() + 1);
        otp.setUsed(true);
        repository.save(otp);
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

    private OtpType resolveOtpType(String email, String phone) {
        if (!ValidationUtil.isBlank(email)) {
            return OtpType.EMAIL_VERIFICATION;
        }
        if (!ValidationUtil.isBlank(phone)) {
            return OtpType.PHONE_VERIFICATION;
        }
        return OtpType.LOGIN;
    }

    private String generateOtpCode() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }

}
