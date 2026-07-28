package com.onedayworker.auth.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class IdentityRoleDto extends BaseEntityDto {

    private Long identityId;
    private Long roleId;
}
