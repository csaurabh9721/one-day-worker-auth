package com.onedayworker.auth.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BaseEntityDto {

    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
