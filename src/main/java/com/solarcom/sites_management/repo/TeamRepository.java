package com.solarcom.sites_management.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.solarcom.sites_management.domain.Team;

public interface TeamRepository extends MongoRepository<Team, String>{

}
