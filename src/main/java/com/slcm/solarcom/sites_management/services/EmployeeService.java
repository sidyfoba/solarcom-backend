package com.slcm.solarcom.sites_management.services;

import java.util.Optional;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.slcm.sites_management.domain.Employee;
import com.slcm.solarcom.sites_management.repo.EmployeeRepository;

@Service
public class EmployeeService {

	private final EmployeeRepository employeeRepository;

	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public boolean isEmailUnique(String email) {
		return !employeeRepository.existsByEmail(email);
	}

	@Async
	public Employee saveEmployee(Employee employee) {

		 return employeeRepository.save(employee);
	}

	public Optional<Employee> getEmployeeById(String id) {
		return employeeRepository.findById(id);
	}

	public Employee updateEmployee(String id, Employee updatedEmployee) {
		Optional<Employee> existingEmployeeOpt = employeeRepository.findById(id);
		if (existingEmployeeOpt.isPresent()) {
			Employee existingEmployee = existingEmployeeOpt.get();
			// Update fields
			existingEmployee.setFullName(updatedEmployee.getFullName());
			existingEmployee.setEmail(updatedEmployee.getEmail());
			existingEmployee.setPhone(updatedEmployee.getPhone());
			existingEmployee.setAddress(updatedEmployee.getAddress());
			existingEmployee.setDob(updatedEmployee.getDob());
			existingEmployee.setEmergencyContacts(updatedEmployee.getEmergencyContacts());
			existingEmployee.setSocialSecurityNumber(updatedEmployee.getSocialSecurityNumber());

			return employeeRepository.save(existingEmployee);
		} else {
			throw new RuntimeException("Employee not found");
		}
	}
	
	public Employee updateEmployeeDetails(String id, Employee updatedEmployee) {
		Optional<Employee> existingEmployeeOpt = employeeRepository.findById(id);
		if (existingEmployeeOpt.isPresent()) {
			Employee existingEmployee = existingEmployeeOpt.get();
			// Update fields
			
			
			existingEmployee.setJobTitle(updatedEmployee.getJobTitle());
			existingEmployee.setDepartment(updatedEmployee.getDepartment());
			existingEmployee.setEmployeeID(updatedEmployee.getEmployeeID());
			existingEmployee.setStartDate(updatedEmployee.getStartDate());
			existingEmployee.setEmploymentStatus(updatedEmployee.getEmploymentStatus());
			existingEmployee.setWorkSchedule(updatedEmployee.getWorkSchedule());
			

			return employeeRepository.save(existingEmployee);
		} else {
			throw new RuntimeException("Employee not found");
		}
	}
}
