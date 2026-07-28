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
    public IdentityDto findById(Long id) {
        return repository.findById(id)
                .map(IdentityMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Identity not found: " + id));
    }


    @Transactional
    public IdentityDto block(Long id) {
        Identity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Identity not found: " + id));
        entity.setStatus(AccountStatus.BLOCKED);
        return IdentityMapper.toDto(repository.save(entity));
    }

    @Transactional
    public IdentityDto unblock(Long id) {
        Identity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Identity not found: " + id));
        entity.setStatus(AccountStatus.ACTIVE);
        return IdentityMapper.toDto(repository.save(entity));
    }
}
