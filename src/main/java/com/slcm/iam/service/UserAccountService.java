package com.slcm.iam.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.slcm.iam.domain.Customer;
import com.slcm.iam.domain.Role;
import com.slcm.iam.domain.UserAccount;
import com.slcm.iam.domain.UserRole;
import com.slcm.iam.dto.AssignUserRoleRequest;
import com.slcm.iam.dto.CreateUserRequest;
import com.slcm.iam.dto.UserAccountDto;
import com.slcm.iam.dto.UserRoleDto;
import com.slcm.iam.repository.CustomerRepository;
import com.slcm.iam.repository.RoleRepository;
import com.slcm.iam.repository.UserAccountRepository;
import com.slcm.iam.repository.UserRoleRepository;
import com.slcm.iam.dto.UpdateUserRequest;
import com.slcm.iam.dto.ResetPasswordRequest;


@Service
@Transactional
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAccountService(UserAccountRepository userAccountRepository,
                              CustomerRepository customerRepository,
                              RoleRepository roleRepository,
                              UserRoleRepository userRoleRepository,
                              PasswordEncoder passwordEncoder) {
        this.userAccountRepository = userAccountRepository;
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserAccountDto createUser(CreateUserRequest req) {
        if (userAccountRepository.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + req.getUsername());
        }
        if (userAccountRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + req.getEmail());
        }
        String hash = passwordEncoder.encode(req.getPassword());
        UserAccount user = new UserAccount(req.getUsername(), req.getEmail(), hash, req.isInternalUser());
        user = userAccountRepository.save(user);
        return toDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserAccountDto> listUsers() {
        return userAccountRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserAccountDto getUser(Long id) {
        UserAccount user = userAccountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
        return toDto(user);
    }

    public void enableUser(Long id, boolean enabled) {
        UserAccount user = userAccountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));
        user.setEnabled(enabled);
    }

    public UserRoleDto assignUserRole(AssignUserRoleRequest req) {
        UserAccount user = userAccountRepository.findById(req.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + req.getUserId()));
        Customer customer = customerRepository.findById(req.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + req.getCustomerId()));
        Role role = roleRepository.findById(req.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + req.getRoleId()));

        if (userRoleRepository.existsByUserAndCustomerAndRole(user, customer, role)) {
            throw new IllegalArgumentException("User already has this role for this customer");
        }

        UserRole ur = new UserRole(user, customer, role);
        ur = userRoleRepository.save(ur);
        return toDto(ur);
    }

    @Transactional(readOnly = true)
    public List<UserRoleDto> listRolesForUser(Long userId) {
        UserAccount user = userAccountRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        return userRoleRepository.findByUser(user).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private UserAccountDto toDto(UserAccount u) {
        return new UserAccountDto()
                .setId(u.getId())
                .setUsername(u.getUsername())
                .setEmail(u.getEmail())
                .setEnabled(u.isEnabled())
                .setInternalUser(u.isInternalUser());
    }

    private UserRoleDto toDto(UserRole ur) {
        return new UserRoleDto()
                .setId(ur.getId())
                .setUserId(ur.getUser().getId())
                .setUsername(ur.getUser().getUsername())
                .setCustomerId(ur.getCustomer().getId())
                .setCustomerName(ur.getCustomer().getName())
                .setRoleId(ur.getRole().getId())
                .setRoleCode(ur.getRole().getCode());
    }
    public UserAccountDto updateUser(Long id, UpdateUserRequest req) {
        UserAccount user = userAccountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));

        // username
        if (req.getUsername() != null && !req.getUsername().equals(user.getUsername())) {
            if (userAccountRepository.existsByUsername(req.getUsername())) {
                throw new IllegalArgumentException("Username already exists: " + req.getUsername());
            }
            user.setUsername(req.getUsername());
        }

        // email
        if (req.getEmail() != null && !req.getEmail().equals(user.getEmail())) {
            if (userAccountRepository.existsByEmail(req.getEmail())) {
                throw new IllegalArgumentException("Email already exists: " + req.getEmail());
            }
            user.setEmail(req.getEmail());
        }

        // enabled
        if (req.getEnabled() != null) {
            user.setEnabled(req.getEnabled());
        }

        // internal flag
        if (req.getInternalUser() != null) {
            user.setInternalUser(req.getInternalUser());
        }

        // no need to call save explicitly if EntityManager flushes, but it's clearer:
        user = userAccountRepository.save(user);

        return toDto(user);
    }

    public void resetPassword(Long id, ResetPasswordRequest req) {
        if (req.getNewPassword() == null || req.getNewPassword().isBlank()) {
            throw new IllegalArgumentException("New password must not be empty");
        }

        UserAccount user = userAccountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + id));

        String hash = passwordEncoder.encode(req.getNewPassword());
        user.setPasswordHash(hash);
    }

}
