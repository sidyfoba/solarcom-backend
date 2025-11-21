package com.slcm.solarcom.sites_management.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.slcm.sites_management.domain.Project;

public interface ProjectRepository extends MongoRepository<Project, String> {
}

