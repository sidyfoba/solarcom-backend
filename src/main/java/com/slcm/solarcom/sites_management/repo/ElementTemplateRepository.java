package com.slcm.solarcom.sites_management.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.slcm.sites_management.domain.ElementTemplate;
import com.slcm.sites_management.domain.SiteTemplate;

public interface ElementTemplateRepository extends MongoRepository<ElementTemplate, String>, PagingAndSortingRepository<ElementTemplate, String>{
	ElementTemplate findByTemplateName(String templateName);
}
