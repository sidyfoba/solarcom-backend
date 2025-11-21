package com.slcm.solarcom.sites_management.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.slcm.sites_management.domain.SiteTemplate;

public interface SiteTemplateRepo extends MongoRepository<SiteTemplate, String>, PagingAndSortingRepository<SiteTemplate, String>{

 SiteTemplate findByTemplateName(String templateName);
}
