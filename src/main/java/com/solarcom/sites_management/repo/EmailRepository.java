package com.solarcom.sites_management.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.solarcom.sites_management.domain.Email;

public interface EmailRepository extends MongoRepository<Email, String> {
    // Custom query methods if necessary
	 @Query("{ 'keyValueDocument' : ?0 }")
	    List<Email> findByKeyValueDocumentId(String keyValueDocumentId);
}