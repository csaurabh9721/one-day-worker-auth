package com.onedayworker.auth.dto;

import com.onedayworker.auth.util.enums.OtpType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class OtpDto extends BaseEntityDto {

    private Long identityId;
    private String code;
    private OtpType type;
    private Integer attempts;
    private Boolean used;
    private LocalDateTime expiresAt;
}
