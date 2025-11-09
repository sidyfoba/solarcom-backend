package com.solarcom.sites_management.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.solarcom.sites_management.domain.TicketTemplate;

public interface TicketTemplateRepository extends MongoRepository<TicketTemplate, String>, PagingAndSortingRepository<TicketTemplate, String>{
	 TicketTemplate findByTemplateName(String templateName);
}
