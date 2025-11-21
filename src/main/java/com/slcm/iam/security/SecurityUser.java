package com.slcm.iam.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Custom security principal to carry extra info about the user.
 */
public class SecurityUser extends User {

	private final Long userId;
	private final boolean internalUser;

	public SecurityUser(Long userId, boolean internalUser, String username, String password, boolean enabled,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, true, true, true, authorities);
		this.userId = userId;
		this.internalUser = internalUser;
	}

	public Long getUserId() {
		return userId;
	}

	public boolean isInternalUser() {
		return internalUser;
	}
}
