package com.slcm.solarcom.sites_management.repo;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.slcm.sites_management.domain.KeyValueDocument;

public interface KeyValueRepository extends MongoRepository<KeyValueDocument, String> {
}

