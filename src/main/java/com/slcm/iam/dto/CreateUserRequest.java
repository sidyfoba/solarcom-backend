package com.slcm.iam.dto;

/**
 * Used when creating a new user.
 * password is the raw password; it will be encoded in the service layer.
 */
public class CreateUserRequest {

    private String username;
    private String email;
    private String password;
    private boolean internalUser;

    public String getUsername() {
        return username;
    }

    public CreateUserRequest setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CreateUserRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public CreateUserRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isInternalUser() {
        return internalUser;
    }

    public CreateUserRequest setInternalUser(boolean internalUser) {
        this.internalUser = internalUser;
        return this;
    }
}
