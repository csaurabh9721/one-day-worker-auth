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

@Service
@RequiredArgsConstructor
public class IdentityRoleService {

    private final IdentityRoleRepository repository;
    private final IdentityRepository identityRepository;
    private final RoleRepository roleRepository;



    @Transactional
    public IdentityRoleDto assignRole(Long identityId, String roleName) {
        Identity identity = identityRepository.findById(identityId)
                .orElseThrow(() -> new EntityNotFoundException("Identity not found: " + identityId));
        Role role = roleRepository.findByName(roleName == null ? null : roleName.trim().toUpperCase())
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleName));

        IdentityRole entity = new IdentityRole();
        entity.setIdentity(identity);
        entity.setRole(role);
        return IdentityRoleMapper.toDto(repository.save(entity));
    }
}
