package com.onedayworker.auth.service;

import com.onedayworker.auth.dto.RoleDto;
import com.onedayworker.auth.entity.Role;
import com.onedayworker.auth.mapper.RoleMapper;
import com.onedayworker.auth.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    @Transactional(readOnly = true)
    public List<RoleDto> findAll() {
        return repository.findAll().stream()
                .map(RoleMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoleDto findById(UUID id) {
        return repository.findById(id)
                .map(RoleMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + id));
    }

    @Transactional
    public RoleDto create(RoleDto dto) {
        Role entity = RoleMapper.toEntity(dto);
        return RoleMapper.toDto(repository.save(entity));
    }

    @Transactional
    public RoleDto update(UUID id, RoleDto dto) {
        Role entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + id));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return RoleMapper.toDto(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Role not found: " + id);
        }
        repository.deleteById(id);
    }
}
