package com.slcm.solarcom.sites_management.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.slcm.sites_management.domain.Team;

public interface TeamRepository extends MongoRepository<Team, String>{

}
