package com.solarcom.sites_management.web;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solarcom.sites_management.domain.Employee;
import com.solarcom.sites_management.repo.EmployeeRepository;
import com.solarcom.sites_management.services.EmployeeService;
import com.solarcom.sites_management.web.response.EmployeeResponse;
import com.solarcom.sites_management.web.response.ErrorResponse;

@RestController
@RequestMapping("/api/hr/employee")
public class EmployeeController {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	private final EmployeeRepository employeeRepository;
	private final EmployeeService employeeService;

	public EmployeeController(EmployeeRepository employeeRepository, EmployeeService employeeService) {
		this.employeeRepository = employeeRepository;
		this.employeeService = employeeService;
	}

	@PostMapping("/personal-info")
	public ResponseEntity<EmployeeResponse> submitEmployeeInfo(@RequestBody Employee employee) {
	    if (!employeeService.isEmailUnique(employee.getEmail())) {
	        return ResponseEntity.status(HttpStatus.CONFLICT)
	                .body(new EmployeeResponse("Email is already in use.", null));
	    }

	    Employee savedEmployee = employeeService.saveEmployee(employee);
	    EmployeeResponse response = new EmployeeResponse("Employee information submitted successfully!", savedEmployee);
	    return ResponseEntity.ok(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") String id) {
		Optional<Employee> employee = employeeService.getEmployeeById(id);
		if (employee.isPresent()) {
			return ResponseEntity.ok(employee.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id")String id, @RequestBody Employee updatedEmployee) {
		try {
			Employee employee = employeeService.updateEmployee(id, updatedEmployee);
			return ResponseEntity.ok(employee);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@PutMapping("/personal-details/{newID}")
	public ResponseEntity<Employee> updateEmployeeDetails(@PathVariable("newID")String id, @RequestBody Employee updatedEmployee) {
		try {
			Employee employee = employeeService.updateEmployeeDetails(id, updatedEmployee);
			return ResponseEntity.ok(employee);
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}


	@GetMapping("/all")
	public ResponseEntity<?> getAllEmployees() {
		try {

			List<Employee> employees = employeeRepository.findAll();

			return new ResponseEntity<>(employees, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error fetching templates", e);

			// Return a detailed error response
			return new ResponseEntity<>(new ErrorResponse("Failed to fetch Employees", e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
