package com.onedayworker.auth.mapper;

import com.onedayworker.auth.dto.RoleDto;
import com.onedayworker.auth.entity.Role;

public final class RoleMapper {

    private RoleMapper() {
    }

    public static RoleDto toDto(Role entity) {
        if (entity == null) {
            return null;
        }

        RoleDto dto = new RoleDto();
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    public static Role toEntity(RoleDto dto) {
        if (dto == null) {
            return null;
        }

        Role entity = Role.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
        entity.setId(dto.getId());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        return entity;
    }
}
