package com.solarcom.sites_management.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solarcom.sites_management.domain.JobPosition;
import com.solarcom.sites_management.repo.JobPositionRepository;

@Service
public class JobPositionService {

	@Autowired
	private JobPositionRepository jobPositionRepository;

	public List<JobPosition> getAllJobPositions() {
		return jobPositionRepository.findAll();
	}

	public JobPosition saveJobPosition(JobPosition jobPosition) {
		return jobPositionRepository.save(jobPosition);
	}

	public void deleteJobPosition(String id) {
		jobPositionRepository.deleteById(id);
	}
}
