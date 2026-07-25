package com.onedayworker.auth.mapper;

import com.onedayworker.auth.dto.IdentityDto;
import com.onedayworker.auth.entity.Identity;

public final class IdentityMapper {

    private IdentityMapper() {
    }

    public static IdentityDto toDto(Identity entity) {
        if (entity == null) {
            return null;
        }

        IdentityDto dto = new IdentityDto();
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setPassword(entity.getPassword());
        dto.setStatus(entity.getStatus());
        dto.setEmailVerified(entity.getEmailVerified());
        dto.setPhoneVerified(entity.getPhoneVerified());
        dto.setFailedLoginAttempts(entity.getFailedLoginAttempts());
        dto.setAccountLockedUntil(entity.getAccountLockedUntil());
        dto.setLastLoginAt(entity.getLastLoginAt());
        return dto;
    }

    public static Identity toEntity(IdentityDto dto) {
        if (dto == null) {
            return null;
        }

        Identity entity = Identity.builder()
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .password(dto.getPassword())
                .status(dto.getStatus())
                .emailVerified(dto.getEmailVerified())
                .phoneVerified(dto.getPhoneVerified())
                .failedLoginAttempts(dto.getFailedLoginAttempts())
                .accountLockedUntil(dto.getAccountLockedUntil())
                .lastLoginAt(dto.getLastLoginAt())
                .build();
        entity.setId(dto.getId());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }
}
