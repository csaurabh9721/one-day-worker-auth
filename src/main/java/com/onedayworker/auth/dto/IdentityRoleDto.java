package com.onedayworker.auth.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class IdentityRoleDto extends BaseEntityDto {

    private UUID identityId;
    private UUID roleId;
}
