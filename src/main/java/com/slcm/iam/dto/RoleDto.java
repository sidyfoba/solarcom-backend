package com.slcm.iam.dto;

public class RoleDto {

    private Long id;
    private String code;
    private String name;
    private String description;

    public Long getId() {
        return id;
    }

    public RoleDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public RoleDto setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public RoleDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RoleDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
