package com.slcm.solarcom.sites_management.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.slcm.sites_management.domain.Element;
import com.slcm.sites_management.domain.ElementHistory;
import com.slcm.solarcom.sites_management.repo.ElementHistoryRepository;
import com.slcm.solarcom.sites_management.repo.ElementRepository;

@Service
public class ElementHistoryService {

	private final ElementHistoryRepository elementHistoryRepository;
	private final ElementRepository elementRepository;
	public ElementHistoryService(ElementHistoryRepository elementHistoryRepository,ElementRepository elementRepository) {
		this.elementHistoryRepository = elementHistoryRepository;
		this.elementRepository = elementRepository;
	}

	public void updateElementSite(String elementId, String newSiteId) {
	    Element element = elementRepository.findById(elementId).orElseThrow(() -> new RuntimeException("Element not found"));
	    String oldSiteId = element.getCurrentSiteId();

	    // Update the element's current site
	    element.setCurrentSiteId(newSiteId);
	    elementRepository.save(element);

	    // Record the change in the history
	    ElementHistory history = new ElementHistory();
	    history.setElementId(elementId);
	    history.setPreviousSiteId(oldSiteId);
	    history.setNewSiteId(newSiteId);
	    history.setChangeDate(LocalDateTime.now());
	    elementHistoryRepository.save(history);
	}

}
