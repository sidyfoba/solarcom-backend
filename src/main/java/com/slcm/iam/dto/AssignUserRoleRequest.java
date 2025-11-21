package com.slcm.iam.dto;

/**
 * For assigning a role to a user within a specific customer (tenant).
 */
public class AssignUserRoleRequest {

    private Long userId;
    private Long customerId;
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public AssignUserRoleRequest setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public AssignUserRoleRequest setCustomerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public Long getRoleId() {
        return roleId;
    }

    public AssignUserRoleRequest setRoleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }
}
