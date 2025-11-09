package com.solarcom.sites_management.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.solarcom.sites_management.domain.Project;

public interface ProjectRepository extends MongoRepository<Project, String> {
}

