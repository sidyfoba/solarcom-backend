package com.slcm.iam.service;

import com.slcm.iam.domain.Permission;
import com.slcm.iam.dto.PermissionDto;
import com.slcm.iam.repository.PermissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public PermissionDto createPermission(PermissionDto dto) {
        if (permissionRepository.existsByCode(dto.getCode())) {
            throw new IllegalArgumentException("Permission code already exists: " + dto.getCode());
        }
        Permission p = new Permission(dto.getCode(), dto.getDescription());
        p = permissionRepository.save(p);
        return toDto(p);
    }

    @Transactional(readOnly = true)
    public List<PermissionDto> listPermissions() {
        return permissionRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private PermissionDto toDto(Permission p) {
        return new PermissionDto()
                .setId(p.getId())
                .setCode(p.getCode())
                .setDescription(p.getDescription());
    }
}
