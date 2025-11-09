package com.solarcom.sites_management.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.solarcom.sites_management.domain.JobPosition;

public interface JobPositionRepository extends MongoRepository<JobPosition, String> {

}
