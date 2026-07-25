package com.onedayworker.auth.service;

import com.onedayworker.auth.dto.RefreshTokenDto;
import com.onedayworker.auth.entity.Identity;
import com.onedayworker.auth.entity.RefreshToken;
import com.onedayworker.auth.mapper.RefreshTokenMapper;
import com.onedayworker.auth.repository.RefreshTokenRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    @Transactional(readOnly = true)
    public List<RefreshTokenDto> findAll() {
        return repository.findAll().stream()
                .map(RefreshTokenMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public RefreshTokenDto findById(UUID id) {
        return repository.findById(id)
                .map(RefreshTokenMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("RefreshToken not found: " + id));
    }

    @Transactional
    public RefreshTokenDto create(RefreshTokenDto dto) {
        RefreshToken entity = RefreshTokenMapper.toEntity(dto);
        return RefreshTokenMapper.toDto(repository.save(entity));
    }

    @Transactional
    public RefreshTokenDto update(UUID id, RefreshTokenDto dto) {
        RefreshToken entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("RefreshToken not found: " + id));
        entity.setIdentity(dto.getIdentityId() != null ? buildIdentity(dto.getIdentityId()) : null);
        entity.setToken(dto.getToken());
        entity.setExpiresAt(dto.getExpiresAt());
        entity.setRevoked(dto.getRevoked());
        return RefreshTokenMapper.toDto(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("RefreshToken not found: " + id);
        }
        repository.deleteById(id);
    }

    private Identity buildIdentity(UUID identityId) {
        Identity identity = new Identity();
        identity.setId(identityId);
        return identity;
    }
}
