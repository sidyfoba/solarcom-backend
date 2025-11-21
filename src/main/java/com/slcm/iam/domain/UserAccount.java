package com.slcm.iam.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Represents a user who can log into the system. A user can be internal (your
 * staff) or external (customer's staff).
 *
 * Tenant binding is done via UserRole, not directly here, so one UserAccount
 * could theoretically belong to multiple tenants via different roles.
 */
@Entity
@Table(name = "user_accounts", uniqueConstraints = {
		@UniqueConstraint(name = "uk_user_username", columnNames = "username"),
		@UniqueConstraint(name = "uk_user_email", columnNames = "email") })
public class UserAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Unique username used for login.
	 */
	@Column(nullable = false, length = 100)
	private String username;

	/**
	 * Email for communication / password reset.
	 */
	@Column(nullable = false, length = 255)
	private String email;

	/**
	 * Hashed password (BCrypt / Argon2, etc.).
	 */
	@Column(name = "password_hash", nullable = false, length = 255)
	private String passwordHash;

	/**
	 * If false, the user cannot log in (disabled).
	 */
	@Column(nullable = false)
	private boolean enabled = true;

	/**
	 * Indicates if this is an internal platform user (your staff) or a customer
	 * user. - true => your own operator/support/admin - false => customer’s
	 * operator/admin
	 */
	@Column(name = "internal_user", nullable = false)
	private boolean internalUser = false;

	// ==== Constructors ====

	protected UserAccount() {
		// JPA only
	}

	public UserAccount(String username, String email, String passwordHash, boolean internalUser) {
		this.username = username;
		this.email = email;
		this.passwordHash = passwordHash;
		this.internalUser = internalUser;
		this.enabled = true;
	}

	// ==== Getters / Setters ====

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public boolean isInternalUser() {
		return internalUser;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setInternalUser(boolean internalUser) {
		this.internalUser = internalUser;
	}
}
