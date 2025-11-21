package com.slcm.iam.repository;


import com.slcm.iam.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByTenantKey(String tenantKey);

    boolean existsByTenantKey(String tenantKey);
    boolean existsByName(String name);
}

