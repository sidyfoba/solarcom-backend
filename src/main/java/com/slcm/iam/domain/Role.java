package com.slcm.iam.domain;

import jakarta.persistence.*;

/**
 * A Role is a named set of permissions, e.g.: - TENANT_ADMIN - INVENTORY_VIEWER
 * - BILLING_MANAGER
 *
 * Roles are attached to users per customer via UserRole.
 */
@Entity
@Table(name = "roles", uniqueConstraints = { @UniqueConstraint(name = "uk_role_code", columnNames = "code") })
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Technical code of the role, used in security checks. Example: "TENANT_ADMIN",
	 * "INVENTORY_VIEWER".
	 */
	@Column(nullable = false, length = 100)
	private String code;

	/**
	 * Human-friendly name.
	 */
	@Column(nullable = false, length = 200)
	private String name;

	/**
	 * Optional description.
	 */
	@Column(length = 1000)
	private String description;

	// ==== Constructors ====

	protected Role() {
		// JPA only
	}

	public Role(String code, String name) {
		this.code = code;
		this.name = name;
	}

	// ==== Getters / Setters ====

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
