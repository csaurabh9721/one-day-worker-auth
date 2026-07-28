package com.onedayworker.auth.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class BaseEntityDto {

    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
