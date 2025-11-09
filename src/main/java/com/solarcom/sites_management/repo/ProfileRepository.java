package com.solarcom.sites_management.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.solarcom.sites_management.domain.Profile;

public interface ProfileRepository extends MongoRepository<Profile, String> {
}

