package com.solarcom.sites_management.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.solarcom.sites_management.domain.AssignmentsList;

@Repository
public interface AssignmentsListRepository extends MongoRepository<AssignmentsList, String> {

}
