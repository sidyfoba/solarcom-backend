package com.slcm.iam.service;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.slcm.iam.domain.Customer;
import com.slcm.iam.domain.CustomerStatus;
import com.slcm.iam.dto.CreateCustomerRequest;
import com.slcm.iam.dto.CustomerDto;
import com.slcm.iam.repository.CustomerRepository;

@Service
@Transactional
public class CustomerService {
	private static final String TENANT_KEY_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int TENANT_KEY_LENGTH = 24; // total length after prefix ≈ 28
    private final SecureRandom secureRandom = new SecureRandom();

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
//
//    public CustomerDto createCustomer(CreateCustomerRequest req) {
//        if (customerRepository.existsByTenantKey(req.getTenantKey())) {
//            throw new IllegalArgumentException("Tenant key already exists: " + req.getTenantKey());
//        }
//        Customer customer = new Customer(req.getName(), req.getTenantKey());
//        customer.setStatus(CustomerStatus.ACTIVE);
//        customer = customerRepository.save(customer);
//        return toDto(customer);
//    }
    public CustomerDto createCustomer(CreateCustomerRequest req) {
        if (req.getName() == null || req.getName().isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }

        // name must be unique
        if (customerRepository.existsByName(req.getName())) {
            throw new IllegalArgumentException("Customer name already exists: " + req.getName());
        }

        // tenant key: always generated, ignore any value from request
        String tenantKey = generateUniqueTenantKey();

        Customer customer = new Customer(req.getName(), tenantKey);
        customer.setStatus(CustomerStatus.ACTIVE);
        customer = customerRepository.save(customer);
        return toDto(customer);
    }



    @Transactional(readOnly = true)
    public List<CustomerDto> listCustomers() {
        return customerRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerDto getCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + id));
        return toDto(customer);
    }

//    public CustomerDto updateCustomer(Long id, CreateCustomerRequest req) {
//        Customer customer = customerRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + id));
//        customer.setName(req.getName());
//        customer.setTenantKey(req.getTenantKey());
//        return toDto(customer);
//    }
//    public CustomerDto updateCustomer(Long id, CreateCustomerRequest req) {
//        Customer customer = customerRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + id));
//        customer.setName(req.getName());
//        customer.setTenantKey(req.getTenantKey());
//        return toDto(customer);
//    }
    
    public CustomerDto updateCustomer(Long id, CreateCustomerRequest req) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + id));

        if (req.getName() != null && !req.getName().isBlank()
                && !req.getName().equals(customer.getName())) {

            if (customerRepository.existsByName(req.getName())) {
                throw new IllegalArgumentException("Customer name already exists: " + req.getName());
            }

            customer.setName(req.getName());
        }

        // tenantKey is NOT changed here

        return toDto(customer);
    }




    public void changeStatus(Long id, CustomerStatus status) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + id));
        customer.setStatus(status);
    }

    private CustomerDto toDto(Customer c) {
        return new CustomerDto()
                .setId(c.getId())
                .setName(c.getName())
                .setTenantKey(c.getTenantKey())
                .setStatus(c.getStatus());
    }
    private String generateRandomTenantKey() {
        StringBuilder sb = new StringBuilder("ten_"); // prefix like Google/AWS style

        for (int i = 0; i < TENANT_KEY_LENGTH; i++) {
            int idx = secureRandom.nextInt(TENANT_KEY_CHARS.length());
            sb.append(TENANT_KEY_CHARS.charAt(idx));
        }

        return sb.toString();
    }

    private String generateUniqueTenantKey() {
        String key;
        do {
            key = generateRandomTenantKey();
        } while (customerRepository.existsByTenantKey(key));
        return key;
    }
//    private String generateTenantKeyFromName(String name) {
//        if (name == null || name.isBlank()) {
//            throw new IllegalArgumentException("Name is required to generate tenant key");
//        }
//
//        // basic slug: lower-case, non alphanumeric -> '-', trim dashes
//        String base = name
//                .toLowerCase()
//                .replaceAll("[^a-z0-9]+", "-")
//                .replaceAll("^-+|-+$", "");
//
//        if (base.isBlank()) {
//            base = "tenant";
//        }
//
//        String candidate = base;
//        int counter = 1;
//        // ensure uniqueness
//        while (customerRepository.existsByTenantKey(candidate)) {
//            candidate = base + "-" + counter;
//            counter++;
//        }
//
//        return candidate;
//    }
    

}
