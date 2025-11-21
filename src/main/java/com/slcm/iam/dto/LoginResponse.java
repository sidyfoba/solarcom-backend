package com.slcm.iam.dto;

import java.util.List;

public class LoginResponse {

    private String token;
    private String tokenType = "Bearer";
    private String username;
    private List<String> permissions;

    public String getToken() {
        return token;
    }

    public LoginResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public String getTokenType() {
        return tokenType;
    }

    public LoginResponse setTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public LoginResponse setUsername(String username) {
        this.username = username;
        return this;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public LoginResponse setPermissions(List<String> permissions) {
        this.permissions = permissions;
        return this;
    }
}
