package com.onedayworker.auth.mapper;

import com.onedayworker.auth.dto.RefreshTokenDto;
import com.onedayworker.auth.entity.Identity;
import com.onedayworker.auth.entity.RefreshToken;

public final class RefreshTokenMapper {

    private RefreshTokenMapper() {
    }

    public static RefreshTokenDto toDto(RefreshToken entity) {
        if (entity == null) {
            return null;
        }

        RefreshTokenDto dto = new RefreshTokenDto();
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setIdentityId(entity.getIdentity() != null ? entity.getIdentity().getId() : null);
        dto.setToken(entity.getToken());
        dto.setExpiresAt(entity.getExpiresAt());
        dto.setRevoked(entity.getRevoked());
        return dto;
    }

    public static RefreshToken toEntity(RefreshTokenDto dto) {
        if (dto == null) {
            return null;
        }

        RefreshToken entity = RefreshToken.builder()
                .token(dto.getToken())
                .expiresAt(dto.getExpiresAt())
                .revoked(dto.getRevoked())
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
