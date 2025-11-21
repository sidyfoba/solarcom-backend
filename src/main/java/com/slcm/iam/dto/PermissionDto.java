package com.slcm.iam.dto;

public class PermissionDto {

    private Long id;
    private String code;
    private String description;

    public Long getId() {
        return id;
    }

    public PermissionDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public PermissionDto setCode(String code) {
        this.code = code;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PermissionDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
