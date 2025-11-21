package com.slcm.iam.repository;

import com.slcm.iam.domain.Customer;
import com.slcm.iam.domain.Role;
import com.slcm.iam.domain.UserAccount;
import com.slcm.iam.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> findByUser(UserAccount user);

    List<UserRole> findByCustomer(Customer customer);

    List<UserRole> findByUserAndCustomer(UserAccount user, Customer customer);

    Optional<UserRole> findByUserAndCustomerAndRole(UserAccount user, Customer customer, Role role);

    boolean existsByUserAndCustomerAndRole(UserAccount user, Customer customer, Role role);
}
