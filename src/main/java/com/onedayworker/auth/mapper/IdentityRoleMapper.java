package com.onedayworker.auth.mapper;

import com.onedayworker.auth.dto.IdentityRoleDto;
import com.onedayworker.auth.entity.Identity;
import com.onedayworker.auth.entity.IdentityRole;
import com.onedayworker.auth.entity.Role;

public final class IdentityRoleMapper {

    private IdentityRoleMapper() {
    }

    public static IdentityRoleDto toDto(IdentityRole entity) {
        if (entity == null) {
            return null;
        }

        IdentityRoleDto dto = new IdentityRoleDto();
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setIdentityId(entity.getIdentity() != null ? entity.getIdentity().getId() : null);
        dto.setRoleId(entity.getRole() != null ? entity.getRole().getId() : null);
        return dto;
    }

    public static IdentityRole toEntity(IdentityRoleDto dto) {
        if (dto == null) {
            return null;
        }

        IdentityRole entity = new IdentityRole();
        entity.setId(dto.getId());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());

        if (dto.getIdentityId() != null) {
            Identity identity = new Identity();
            identity.setId(dto.getIdentityId());
            entity.setIdentity(identity);
        }

        if (dto.getRoleId() != null) {
            Role role = new Role();
            role.setId(dto.getRoleId());
            entity.setRole(role);
        }

        return entity;
    }
}
