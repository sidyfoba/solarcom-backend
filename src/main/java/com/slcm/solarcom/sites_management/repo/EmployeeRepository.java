package com.slcm.solarcom.sites_management.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.slcm.sites_management.domain.Employee;

public interface EmployeeRepository
		extends MongoRepository<Employee, String>, PagingAndSortingRepository<Employee, String> {
	  boolean existsByEmail(String email);

}
