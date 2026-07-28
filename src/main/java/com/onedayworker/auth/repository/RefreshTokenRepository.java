package com.onedayworker.auth.repository;

import com.onedayworker.auth.entity.Identity;
import com.onedayworker.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findAllByIdentityAndRevokedFalse(Identity identity);
}
