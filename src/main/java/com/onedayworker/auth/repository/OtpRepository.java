package com.onedayworker.auth.repository;

import com.onedayworker.auth.entity.Otp;
import com.onedayworker.auth.util.enums.OtpType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OtpRepository extends JpaRepository<Otp, UUID> {

    Optional<Otp> findByCodeAndTypeAndUsedFalse(String code, OtpType type);
}
