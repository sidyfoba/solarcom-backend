package com.slcm.iam.domain;


import jakarta.persistence.*;

/**
 * Permission is a fine-grained right, e.g.:
 * - INVENTORY_VIEW
 * - INVENTORY_EDIT
 * - BILLING_VIEW
 * - PROVISION_SERVICE
 *
 * Roles are associated to permissions via RolePermission.
 */
@Entity
@Table(
        name = "permissions",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_permission_code", columnNames = "code")
        }
)
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Technical code of the permission.
     * Example: "INVENTORY_VIEW", "INVENTORY_EDIT".
     */
    @Column(nullable = false, length = 100)
    private String code;

    /**
     * Optional description.
     */
    @Column(length = 1000)
    private String description;

    // ==== Constructors ====

    protected Permission() {
        // JPA only
    }

    public Permission(String code) {
        this.code = code;
    }

    public Permission(String code, String description) {
        this.code = code;
        this.description = description;
    }

    // ==== Getters / Setters ====

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

