package com.slcm.iam.dto;



import com.slcm.iam.domain.CustomerStatus;

public class CustomerDto {

    private Long id;
    private String name;
    private String tenantKey;
    private CustomerStatus status;

    // Getters / setters

    public Long getId() {
        return id;
    }

    public CustomerDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CustomerDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getTenantKey() {
        return tenantKey;
    }

    public CustomerDto setTenantKey(String tenantKey) {
        this.tenantKey = tenantKey;
        return this;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public CustomerDto setStatus(CustomerStatus status) {
        this.status = status;
        return this;
    }
}
