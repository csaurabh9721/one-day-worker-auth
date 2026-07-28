package com.onedayworker.auth.repository;

import com.onedayworker.auth.entity.IdentityRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IdentityRoleRepository extends JpaRepository<IdentityRole, Long> {
}
