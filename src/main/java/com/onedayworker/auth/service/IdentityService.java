package com.onedayworker.auth.service;

import com.onedayworker.auth.dto.IdentityDto;
import com.onedayworker.auth.entity.Identity;
import com.onedayworker.auth.mapper.IdentityMapper;
import com.onedayworker.auth.repository.IdentityRepository;
import com.onedayworker.auth.util.enums.AccountStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IdentityService {

    private final IdentityRepository repository;

    @Transactional(readOnly = true)
    public List<IdentityDto> findAll() {
        return repository.findAll().stream()
                .map(IdentityMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public IdentityDto findById(UUID id) {
        return repository.findById(id)
                .map(IdentityMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Identity not found: " + id));
    }

    @Transactional
    public IdentityDto create(IdentityDto dto) {
        Identity entity = IdentityMapper.toEntity(dto);
        return IdentityMapper.toDto(repository.save(entity));
    }

    @Transactional
    public IdentityDto update(UUID id, IdentityDto dto) {
        Identity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Identity not found: " + id));
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(dto.getPassword());
        entity.setStatus(dto.getStatus());
        entity.setEmailVerified(dto.getEmailVerified());
        entity.setPhoneVerified(dto.getPhoneVerified());
        entity.setFailedLoginAttempts(dto.getFailedLoginAttempts());
        entity.setAccountLockedUntil(dto.getAccountLockedUntil());
        entity.setLastLoginAt(dto.getLastLoginAt());
        return IdentityMapper.toDto(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Identity not found: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional
    public IdentityDto block(UUID id) {
        Identity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Identity not found: " + id));
        entity.setStatus(AccountStatus.BLOCKED);
        return IdentityMapper.toDto(repository.save(entity));
    }

    @Transactional
    public IdentityDto unblock(UUID id) {
        Identity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Identity not found: " + id));
        entity.setStatus(AccountStatus.ACTIVE);
        return IdentityMapper.toDto(repository.save(entity));
    }
}
