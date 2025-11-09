package com.solarcom.sites_management.web.response;

import com.solarcom.sites_management.domain.Employee;

public class EmployeeResponse {
    private String message;
    private Employee employee;

    public EmployeeResponse(String message, Employee employee) {
        this.message = message;
        this.employee = employee;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public Employee getEmployee() {
        return employee;
    }
}

