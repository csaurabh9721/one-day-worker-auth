package com.onedayworker.auth.service;

import com.onedayworker.auth.dto.IdentityRoleDto;
import com.onedayworker.auth.entity.Identity;
import com.onedayworker.auth.entity.IdentityRole;
import com.onedayworker.auth.entity.Role;
import com.onedayworker.auth.mapper.IdentityRoleMapper;
import com.onedayworker.auth.repository.IdentityRepository;
import com.onedayworker.auth.repository.IdentityRoleRepository;
import com.onedayworker.auth.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IdentityRoleService {

    private final IdentityRoleRepository repository;
    private final IdentityRepository identityRepository;
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public List<IdentityRoleDto> findAll() {
        return repository.findAll().stream()
                .map(IdentityRoleMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public IdentityRoleDto findById(UUID id) {
        return repository.findById(id)
                .map(IdentityRoleMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("IdentityRole not found: " + id));
    }

    @Transactional
    public IdentityRoleDto create(IdentityRoleDto dto) {
        IdentityRole entity = IdentityRoleMapper.toEntity(dto);
        return IdentityRoleMapper.toDto(repository.save(entity));
    }

    @Transactional
    public IdentityRoleDto update(UUID id, IdentityRoleDto dto) {
        IdentityRole entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("IdentityRole not found: " + id));
        entity.setIdentity(dto.getIdentityId() != null ? buildIdentity(dto.getIdentityId()) : null);
        entity.setRole(dto.getRoleId() != null ? buildRole(dto.getRoleId()) : null);
        return IdentityRoleMapper.toDto(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("IdentityRole not found: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional
    public IdentityRoleDto assignRole(UUID identityId, String roleName) {
        Identity identity = identityRepository.findById(identityId)
                .orElseThrow(() -> new EntityNotFoundException("Identity not found: " + identityId));
        Role role = roleRepository.findByName(roleName == null ? null : roleName.trim().toUpperCase())
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleName));

        IdentityRole entity = new IdentityRole();
        entity.setIdentity(identity);
        entity.setRole(role);
        return IdentityRoleMapper.toDto(repository.save(entity));
    }

    private Identity buildIdentity(UUID identityId) {
        Identity identity = new Identity();
        identity.setId(identityId);
        return identity;
    }

    private Role buildRole(UUID roleId) {
        Role role = new Role();
        role.setId(roleId);
        return role;
    }
}
