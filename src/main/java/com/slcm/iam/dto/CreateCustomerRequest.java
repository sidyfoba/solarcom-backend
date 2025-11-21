package com.slcm.iam.dto;

public class CreateCustomerRequest {
	private String name;

	public String getName() {
		return name;
	}

	public CreateCustomerRequest setName(String name) {
		this.name = name;
		return this;
	}
}
