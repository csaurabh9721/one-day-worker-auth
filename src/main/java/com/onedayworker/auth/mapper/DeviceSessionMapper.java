package com.onedayworker.auth.mapper;

import com.onedayworker.auth.dto.DeviceSessionDto;
import com.onedayworker.auth.entity.DeviceSession;
import com.onedayworker.auth.entity.Identity;

public final class DeviceSessionMapper {

    private DeviceSessionMapper() {
    }

    public static DeviceSessionDto toDto(DeviceSession entity) {
        if (entity == null) {
            return null;
        }

        DeviceSessionDto dto = new DeviceSessionDto();
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setIdentityId(entity.getIdentity() != null ? entity.getIdentity().getId() : null);
        dto.setDeviceId(entity.getDeviceId());
        dto.setDeviceName(entity.getDeviceName());
        dto.setOperatingSystem(entity.getOperatingSystem());
        dto.setBrowser(entity.getBrowser());
        dto.setIpAddress(entity.getIpAddress());
        dto.setLastSeenAt(entity.getLastSeenAt());
        dto.setActive(entity.getActive());
        return dto;
    }

    public static DeviceSession toEntity(DeviceSessionDto dto) {
        if (dto == null) {
            return null;
        }

        DeviceSession entity = DeviceSession.builder()
                .deviceId(dto.getDeviceId())
                .deviceName(dto.getDeviceName())
                .operatingSystem(dto.getOperatingSystem())
                .browser(dto.getBrowser())
                .ipAddress(dto.getIpAddress())
                .lastSeenAt(dto.getLastSeenAt())
                .active(dto.getActive())
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
