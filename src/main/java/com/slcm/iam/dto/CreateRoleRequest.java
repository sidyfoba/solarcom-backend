package com.slcm.iam.dto;

public class CreateRoleRequest {

    private String code;
    private String name;
    private String description;

    public String getCode() {
        return code;
    }

    public CreateRoleRequest setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public CreateRoleRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CreateRoleRequest setDescription(String description) {
        this.description = description;
        return this;
    }
}
