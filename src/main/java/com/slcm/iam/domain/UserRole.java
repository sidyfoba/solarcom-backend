package com.slcm.iam.domain;


import jakarta.persistence.*;

/**
 * UserRole links:
 * - a UserAccount
 * - a Customer (tenant)
 * - a Role
 *
 * This makes roles tenant-specific. The same username can have different roles in different customers,
 * if you decide to support that.
 */
@Entity
@Table(
        name = "user_roles",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_role_per_customer",
                        columnNames = {"user_id", "customer_id", "role_id"}
                )
        }
)
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user to whom the role is assigned.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_role_user"))
    private UserAccount user;

    /**
     * The tenant where this role is valid.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_role_customer"))
    private Customer customer;

    /**
     * The role granted to the user within this tenant.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_user_role_role"))
    private Role role;

    // ==== Constructors ====

    protected UserRole() {
        // JPA only
    }

    public UserRole(UserAccount user, Customer customer, Role role) {
        this.user = user;
        this.customer = customer;
        this.role = role;
    }

    // ==== Getters / Setters ====

    public Long getId() {
        return id;
    }

    public UserAccount getUser() {
        return user;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Role getRole() {
        return role;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
