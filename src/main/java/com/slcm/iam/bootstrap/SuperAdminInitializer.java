package com.slcm.iam.bootstrap;

import com.slcm.iam.domain.Customer;
import com.slcm.iam.domain.CustomerStatus;
import com.slcm.iam.domain.Permission;
import com.slcm.iam.domain.Role;
import com.slcm.iam.domain.RolePermission;
import com.slcm.iam.domain.UserAccount;
import com.slcm.iam.domain.UserRole;
import com.slcm.iam.repository.CustomerRepository;
import com.slcm.iam.repository.PermissionRepository;
import com.slcm.iam.repository.RolePermissionRepository;
import com.slcm.iam.repository.RoleRepository;
import com.slcm.iam.repository.UserAccountRepository;
import com.slcm.iam.repository.UserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Initializes a SUPER ADMIN internal user with all permissions.
 *
 * This runs once at startup. If the user already exists, it does nothing.
 */
@Component
public class SuperAdminInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SuperAdminInitializer.class);

    private static final String SUPER_ADMIN_USERNAME = "superadmin";
    private static final String SUPER_ADMIN_EMAIL = "superadmin@local";
    private static final String SUPER_ADMIN_PASSWORD = "ChangeMe123!"; // change in prod!
    private static final String SUPER_ADMIN_ROLE_CODE = "SUPER_ADMIN";

    // Special "platform" customer used to attach the super admin role
    private static final String PLATFORM_TENANT_KEY = "platform";
    private static final String PLATFORM_TENANT_NAME = "Platform Tenant";

    private final UserAccountRepository userAccountRepository;
    private final CustomerRepository customerRepository;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public SuperAdminInitializer(UserAccountRepository userAccountRepository,
                                 CustomerRepository customerRepository,
                                 PermissionRepository permissionRepository,
                                 RoleRepository roleRepository,
                                 RolePermissionRepository rolePermissionRepository,
                                 UserRoleRepository userRoleRepository,
                                 PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.customerRepository = customerRepository;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // If super admin already exists, skip
        if (userAccountRepository.existsByUsername(SUPER_ADMIN_USERNAME)) {
            log.info("Super admin user '{}' already exists. Skipping initialization.", SUPER_ADMIN_USERNAME);
            return;
        }

        log.info("Creating SUPER ADMIN user and role...");

        // 1) Ensure platform customer exists
        Customer platformCustomer = customerRepository.findByTenantKey(PLATFORM_TENANT_KEY)
                .orElseGet(() -> {
                    Customer c = new Customer(PLATFORM_TENANT_NAME, PLATFORM_TENANT_KEY);
                    c.setStatus(CustomerStatus.ACTIVE);
                    c = customerRepository.save(c);
                    log.info("Created platform customer: id={}, tenantKey={}", c.getId(), c.getTenantKey());
                    return c;
                });

        // 2) Ensure SUPER_ADMIN role exists
        Role superAdminRole = roleRepository.findByCode(SUPER_ADMIN_ROLE_CODE)
                .orElseGet(() -> {
                    Role r = new Role(SUPER_ADMIN_ROLE_CODE, "Super Administrator");
                    r.setDescription("Internal super admin with all permissions");
                    r = roleRepository.save(r);
                    log.info("Created SUPER_ADMIN role with id={}", r.getId());
                    return r;
                });

        // 3) Assign all existing permissions to SUPER_ADMIN role
        List<Permission> allPerms = permissionRepository.findAll();
        for (Permission perm : allPerms) {
            if (!rolePermissionRepository.existsByRoleAndPermission(superAdminRole, perm)) {
                RolePermission rp = new RolePermission(superAdminRole, perm);
                rolePermissionRepository.save(rp);
                log.info("Assigned permission {} to SUPER_ADMIN role", perm.getCode());
            }
        }

        // 4) Create internal UserAccount
        String passwordHash = passwordEncoder.encode(SUPER_ADMIN_PASSWORD);
        UserAccount superAdmin = new UserAccount(
                SUPER_ADMIN_USERNAME,
                SUPER_ADMIN_EMAIL,
                passwordHash,
                true // internalUser = true
        );
        superAdmin = userAccountRepository.save(superAdmin);
        log.info("Created super admin user with id={}, username={}", superAdmin.getId(), superAdmin.getUsername());

        // 5) Link SUPER_ADMIN role to platform customer (UserRole)
        if (!userRoleRepository.existsByUserAndCustomerAndRole(superAdmin, platformCustomer, superAdminRole)) {
            UserRole ur = new UserRole(superAdmin, platformCustomer, superAdminRole);
            userRoleRepository.save(ur);
            log.info("Linked SUPER_ADMIN role to user '{}' on platform customer", SUPER_ADMIN_USERNAME);
        }

        log.info("SUPER ADMIN initialization complete. Username='{}', password='{}' (change it in prod!)",
                SUPER_ADMIN_USERNAME, SUPER_ADMIN_PASSWORD);
    }
}
