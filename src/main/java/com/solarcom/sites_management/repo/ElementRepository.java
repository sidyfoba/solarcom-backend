package com.solarcom.sites_management.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.solarcom.sites_management.domain.Element;
import com.solarcom.sites_management.domain.ElementTemplate;
import com.solarcom.sites_management.domain.Site;
import com.solarcom.sites_management.domain.SiteTemplate;

public interface ElementRepository
		extends MongoRepository<Element, String>, PagingAndSortingRepository<Element, String> {
	@Query("{ 'elementTemplate.id': ?0 }")
	List<Element> findByElementTemplateId(String elementTemplateId);

	List<Element> findByElementTemplateIdAndCurrentSiteIdIsNull(String elementTemplateId);

	List<Element> findByElementTemplate(ElementTemplate template);

	boolean existsByElementNameAndElementTemplate(String elementName, ElementTemplate elementTemplate);

	Element findByElementNameAndElementTemplate(String elementName, ElementTemplate elementTemplate);

}
