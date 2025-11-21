package com.slcm.iam.dto;

public class UserRoleDto {

    private Long id;
    private Long userId;
    private String username;
    private Long customerId;
    private String customerName;
    private Long roleId;
    private String roleCode;

    public Long getId() {
        return id;
    }

    public UserRoleDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public UserRoleDto setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserRoleDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public UserRoleDto setCustomerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getCustomerName() {
        return customerName;
    }

    public UserRoleDto setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public Long getRoleId() {
        return roleId;
    }

    public UserRoleDto setRoleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public UserRoleDto setRoleCode(String roleCode) {
        this.roleCode = roleCode;
        return this;
    }
}
