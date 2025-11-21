package com.slcm.sites_management.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "employees")
public class Employee {
	@Id
	private String id;

	// emplpyee infos
	private String fullName;
	private String email;
	private String phone;
	private String address;
	private String dob; // Date of Birth
	private String emergencyContacts;
	private String socialSecurityNumber;

	// employee details
	private String jobTitle;
	private String department;
	private String employeeID;
	private String startDate;
	private String employmentStatus;
	private String workSchedule;

}
