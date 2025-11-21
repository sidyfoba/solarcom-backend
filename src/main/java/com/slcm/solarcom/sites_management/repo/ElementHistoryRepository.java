package com.slcm.solarcom.sites_management.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.slcm.sites_management.domain.ElementHistory;

public interface ElementHistoryRepository extends MongoRepository<ElementHistory, String>, PagingAndSortingRepository<ElementHistory, String>{

}
