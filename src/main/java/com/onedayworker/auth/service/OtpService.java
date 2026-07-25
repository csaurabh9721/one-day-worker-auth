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

    @Transactional(readOnly = true)
    public List<OtpDto> findAll() {
        return repository.findAll().stream()
                .map(OtpMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public OtpDto findById(UUID id) {
        return repository.findById(id)
                .map(OtpMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Otp not found: " + id));
    }

    @Transactional
    public OtpDto create(OtpDto dto) {
        Otp entity = OtpMapper.toEntity(dto);
        return OtpMapper.toDto(repository.save(entity));
    }

    @Transactional
    public OtpDto update(UUID id, OtpDto dto) {
        Otp entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Otp not found: " + id));
        entity.setIdentity(dto.getIdentityId() != null ? buildIdentity(dto.getIdentityId()) : null);
        entity.setCode(dto.getCode());
        entity.setType(dto.getType());
        entity.setAttempts(dto.getAttempts());
        entity.setUsed(dto.getUsed());
        entity.setExpiresAt(dto.getExpiresAt());
        return OtpMapper.toDto(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Otp not found: " + id);
        }
        repository.deleteById(id);
    }

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

    private Identity buildIdentity(UUID identityId) {
        Identity identity = new Identity();
        identity.setId(identityId);
        return identity;
    }
}
