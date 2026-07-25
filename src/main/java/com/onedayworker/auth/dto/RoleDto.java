package com.onedayworker.auth.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDto extends BaseEntityDto {

    private String name;
    private String description;
}
