package com.slcm.iam.dto;

public class UserAccountDto {

    private Long id;
    private String username;
    private String email;
    private boolean enabled;
    private boolean internalUser;

    public Long getId() {
        return id;
    }

    public UserAccountDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserAccountDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserAccountDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public UserAccountDto setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public boolean isInternalUser() {
        return internalUser;
    }

    public UserAccountDto setInternalUser(boolean internalUser) {
        this.internalUser = internalUser;
        return this;
    }
}
