package com.onedayworker.auth.dto;


import com.onedayworker.auth.util.enums.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record WorkerResponse(

        Long id,

        Long identityId,

        String firstName,

        String lastName,

        String phone,

        String email,

        String profileImageUrl,

        Gender gender,

        LocalDate dateOfBirth,

        Integer experienceYears,

        BigDecimal averageRating,

        Integer totalCompletedJobs,

        Boolean profileVerified,

        Boolean active,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}