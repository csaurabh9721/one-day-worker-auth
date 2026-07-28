package com.onedayworker.auth.repository;

import com.onedayworker.auth.entity.DeviceSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceSessionRepository extends JpaRepository<DeviceSession, Long> {
}
