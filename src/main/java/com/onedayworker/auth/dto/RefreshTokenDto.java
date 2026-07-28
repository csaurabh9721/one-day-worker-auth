package com.onedayworker.auth.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class RefreshTokenDto extends BaseEntityDto {

    private Long identityId;
    private String token;
    private LocalDateTime expiresAt;
    private Boolean revoked;
}
