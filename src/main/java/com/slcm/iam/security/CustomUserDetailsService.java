package com.slcm.iam.security;

import com.slcm.iam.domain.Permission;
import com.slcm.iam.domain.RolePermission;
import com.slcm.iam.domain.UserAccount;
import com.slcm.iam.domain.UserRole;
import com.slcm.iam.repository.RolePermissionRepository;
import com.slcm.iam.repository.UserAccountRepository;
import com.slcm.iam.repository.UserRoleRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Loads user + authorities from the database.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;
    private final UserRoleRepository userRoleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    public CustomUserDetailsService(UserAccountRepository userAccountRepository,
                                    UserRoleRepository userRoleRepository,
                                    RolePermissionRepository rolePermissionRepository) {
        this.userAccountRepository = userAccountRepository;
        this.userRoleRepository = userRoleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount user = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // All roles of this user (across all customers; you can filter by tenant later if needed)
        List<UserRole> userRoles = userRoleRepository.findByUser(user);

        // Collect permissions from all roles
        Set<Permission> permissions = new HashSet<>();
        for (UserRole ur : userRoles) {
            List<RolePermission> rolePermissions = rolePermissionRepository.findByRole(ur.getRole());
            permissions.addAll(
                    rolePermissions.stream()
                            .map(RolePermission::getPermission)
                            .collect(Collectors.toSet())
            );
        }

        List<GrantedAuthority> authorities = permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.getCode()))
                .collect(Collectors.toList());

        return new SecurityUser(
                user.getId(),
                user.isInternalUser(),
                user.getUsername(),
                user.getPasswordHash(),
                user.isEnabled(),
                authorities
        );
    }
}
