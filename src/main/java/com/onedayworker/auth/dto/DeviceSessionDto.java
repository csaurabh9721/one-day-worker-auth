package com.onedayworker.auth.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceSessionDto extends BaseEntityDto {

    private UUID identityId;
    private String deviceId;
    private String deviceName;
    private String operatingSystem;
    private String browser;
    private String ipAddress;
    private LocalDateTime lastSeenAt;
    private Boolean active;
}
