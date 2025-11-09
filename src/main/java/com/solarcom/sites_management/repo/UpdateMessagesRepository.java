package com.solarcom.sites_management.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.solarcom.sites_management.domain.UpdateMessages;

@Repository
public interface UpdateMessagesRepository extends MongoRepository<UpdateMessages, String> {
    // Custom query methods if needed
}

