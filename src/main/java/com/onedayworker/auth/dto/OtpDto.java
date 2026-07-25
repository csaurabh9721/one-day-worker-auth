package com.onedayworker.auth.dto;

import com.onedayworker.auth.util.enums.OtpType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class OtpDto extends BaseEntityDto {

    private UUID identityId;
    private String code;
    private OtpType type;
    private Integer attempts;
    private Boolean used;
    private LocalDateTime expiresAt;
}
