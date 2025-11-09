package com.solarcom.sites_management.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.solarcom.sites_management.domain.ElementHistory;

public interface ElementHistoryRepository extends MongoRepository<ElementHistory, String>, PagingAndSortingRepository<ElementHistory, String>{

}
