package com.onedayworker.auth.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class RefreshTokenDto extends BaseEntityDto {

    private UUID identityId;
    private String token;
    private LocalDateTime expiresAt;
    private Boolean revoked;
}
