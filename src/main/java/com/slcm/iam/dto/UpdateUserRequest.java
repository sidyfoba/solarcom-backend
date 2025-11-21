// UpdateUserRequest.java
package com.slcm.iam.dto;

public class UpdateUserRequest {
    private String username;
    private String email;
    private Boolean enabled;
    private Boolean internalUser;

    public String getUsername() {
        return username;
    }

    public UpdateUserRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UpdateUserRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public UpdateUserRequest setEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Boolean getInternalUser() {
        return internalUser;
    }

    public UpdateUserRequest setInternalUser(Boolean internalUser) {
        this.internalUser = internalUser;
        return this;
    }
}
