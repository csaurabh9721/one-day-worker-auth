package com.onedayworker.auth.service;

import com.onedayworker.auth.dto.DeviceSessionDto;
import com.onedayworker.auth.entity.DeviceSession;
import com.onedayworker.auth.entity.Identity;
import com.onedayworker.auth.mapper.DeviceSessionMapper;
import com.onedayworker.auth.repository.DeviceSessionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceSessionService {

    private final DeviceSessionRepository repository;

    @Transactional(readOnly = true)
    public List<DeviceSessionDto> findAll() {
        return repository.findAll().stream()
                .map(DeviceSessionMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public DeviceSessionDto findById(UUID id) {
        return repository.findById(id)
                .map(DeviceSessionMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("DeviceSession not found: " + id));
    }

    @Transactional
    public DeviceSessionDto create(DeviceSessionDto dto) {
        DeviceSession entity = DeviceSessionMapper.toEntity(dto);
        return DeviceSessionMapper.toDto(repository.save(entity));
    }

    @Transactional
    public DeviceSessionDto update(UUID id, DeviceSessionDto dto) {
        DeviceSession entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DeviceSession not found: " + id));
        entity.setIdentity(dto.getIdentityId() != null ? buildIdentity(dto.getIdentityId()) : null);
        entity.setDeviceId(dto.getDeviceId());
        entity.setDeviceName(dto.getDeviceName());
        entity.setOperatingSystem(dto.getOperatingSystem());
        entity.setBrowser(dto.getBrowser());
        entity.setIpAddress(dto.getIpAddress());
        entity.setLastSeenAt(dto.getLastSeenAt());
        entity.setActive(dto.getActive());
        return DeviceSessionMapper.toDto(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("DeviceSession not found: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }

    private Identity buildIdentity(UUID identityId) {
        Identity identity = new Identity();
        identity.setId(identityId);
        return identity;
    }
}
