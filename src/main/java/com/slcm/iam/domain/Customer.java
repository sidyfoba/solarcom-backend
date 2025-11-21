package com.slcm.iam.domain;

import jakarta.persistence.*;

/**
 * A Customer represents a tenant in your multi-tenant platform. All data
 * (users, devices, services, etc.) is scoped by Customer.
 */
@Entity
@Table(name = "customers", uniqueConstraints = {
		@UniqueConstraint(name = "uk_customer_tenant_key", columnNames = "tenant_key"),
		@UniqueConstraint(name = "uk_customer_name", columnNames = "name") // NEW
})
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Human-readable name of the customer (company name).
	 */
	@Column(nullable = false, length = 200)
	private String name;

	/**
	 * Unique technical key used to identify the tenant (e.g. from subdomain: acme,
	 * telco1).
	 */
	@Column(name = "tenant_key", nullable = false, length = 100)
	private String tenantKey;

	/**
	 * Status of the customer (ACTIVE, SUSPENDED, etc.).
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)
	private CustomerStatus status = CustomerStatus.ACTIVE;

	// ==== Constructors ====

	protected Customer() {
		// JPA only
	}

	public Customer(String name, String tenantKey) {
		this.name = name;
		this.tenantKey = tenantKey;
		this.status = CustomerStatus.ACTIVE;
	}

	// ==== Getters / Setters ====

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getTenantKey() {
		return tenantKey;
	}

	public CustomerStatus getStatus() {
		return status;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTenantKey(String tenantKey) {
		this.tenantKey = tenantKey;
	}

	public void setStatus(CustomerStatus status) {
		this.status = status;
	}
}
