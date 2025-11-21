package com.slcm.iam.repository;

import com.slcm.iam.domain.RolePermission;
import com.slcm.iam.domain.Role;
import com.slcm.iam.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

    List<RolePermission> findByRole(Role role);

    List<RolePermission> findByPermission(Permission permission);

    Optional<RolePermission> findByRoleAndPermission(Role role, Permission permission);

    boolean existsByRoleAndPermission(Role role, Permission permission);
}
