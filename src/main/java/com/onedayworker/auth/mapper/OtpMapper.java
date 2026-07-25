package com.onedayworker.auth.mapper;

import com.onedayworker.auth.dto.OtpDto;
import com.onedayworker.auth.entity.Identity;
import com.onedayworker.auth.entity.Otp;

public final class OtpMapper {

    private OtpMapper() {
    }

    public static OtpDto toDto(Otp entity) {
        if (entity == null) {
            return null;
        }

        OtpDto dto = new OtpDto();
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setIdentityId(entity.getIdentity() != null ? entity.getIdentity().getId() : null);
        dto.setCode(entity.getCode());
        dto.setType(entity.getType());
        dto.setAttempts(entity.getAttempts());
        dto.setUsed(entity.getUsed());
        dto.setExpiresAt(entity.getExpiresAt());
        return dto;
    }

    public static Otp toEntity(OtpDto dto) {
        if (dto == null) {
            return null;
        }

        Otp entity = Otp.builder()
                .code(dto.getCode())
                .type(dto.getType())
                .attempts(dto.getAttempts())
                .used(dto.getUsed())
                .expiresAt(dto.getExpiresAt())
                .build();
        entity.setId(dto.getId());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());

        if (dto.getIdentityId() != null) {
            Identity identity = new Identity();
            identity.setId(dto.getIdentityId());
            entity.setIdentity(identity);
        }

        return entity;
    }
}
