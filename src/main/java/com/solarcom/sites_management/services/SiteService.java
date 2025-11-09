package com.solarcom.sites_management.services;

//src/main/java/com/example/demo/service/SiteService.java

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solarcom.sites_management.domain.Site;
import com.solarcom.sites_management.domain.SiteTemplate;
import com.solarcom.sites_management.repo.SiteRepo;
import com.solarcom.sites_management.repo.SiteTemplateRepo;

@Service
public class SiteService {
	@Autowired
	private SiteRepo siteRepository;

	@Autowired
	private SiteTemplateRepo siteTemplateRepository;

	public List<Site> getSitesByTemplate(String templateId) {

		SiteTemplate template = siteTemplateRepository.findById(templateId).get();

		return siteRepository.findBySiteTemplateId(templateId);
	}

	public Optional<SiteTemplate> getTemplateFields(String templateId) {

		return siteTemplateRepository.findById(templateId);
	}

	public List<SiteTemplate> getAllTemplates() {
		return siteTemplateRepository.findAll();
	}
}
