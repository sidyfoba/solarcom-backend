package com.slcm.iam.service;

import com.slcm.iam.domain.Permission;
import com.slcm.iam.domain.Role;
import com.slcm.iam.domain.RolePermission;
import com.slcm.iam.dto.CreateRoleRequest;
import com.slcm.iam.dto.RoleDto;
import com.slcm.iam.repository.PermissionRepository;
import com.slcm.iam.repository.RolePermissionRepository;
import com.slcm.iam.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public RoleService(RoleRepository roleRepository,
                       PermissionRepository permissionRepository,
                       RolePermissionRepository rolePermissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    public RoleDto createRole(CreateRoleRequest req) {
        if (roleRepository.existsByCode(req.getCode())) {
            throw new IllegalArgumentException("Role code already exists: " + req.getCode());
        }
        Role role = new Role(req.getCode(), req.getName());
        role.setDescription(req.getDescription());
        role = roleRepository.save(role);
        return toDto(role);
    }

    @Transactional(readOnly = true)
    public List<RoleDto> listRoles() {
        return roleRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public void addPermissionToRole(String roleCode, String permissionCode) {
        Role role = roleRepository.findByCode(roleCode)
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleCode));
        Permission permission = permissionRepository.findByCode(permissionCode)
                .orElseThrow(() -> new IllegalArgumentException("Permission not found: " + permissionCode));

        if (rolePermissionRepository.existsByRoleAndPermission(role, permission)) {
            return;
        }
        RolePermission rp = new RolePermission(role, permission);
        rolePermissionRepository.save(rp);
    }

    private RoleDto toDto(Role r) {
        return new RoleDto()
                .setId(r.getId())
                .setCode(r.getCode())
                .setName(r.getName())
                .setDescription(r.getDescription());
    }
}
