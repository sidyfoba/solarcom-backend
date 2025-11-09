package com.solarcom.sites_management.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.solarcom.sites_management.domain.Site;
import com.solarcom.sites_management.domain.SiteTemplate;

public interface SiteRepo extends MongoRepository<Site, String>, PagingAndSortingRepository<Site, String>{
	 // Query method to find sites by siteTemplate ID
    @Query("{ 'siteTemplate.id': ?0 }")
    List<Site> findBySiteTemplateId(String siteTemplateId);
	 List<Site> findBySiteTemplate(SiteTemplate template);
	 boolean existsBySiteNameAndSiteTemplate(String siteName, SiteTemplate siteTemplate);
	 Site findBySiteNameAndSiteTemplate(String siteName, SiteTemplate siteTemplate);

}
