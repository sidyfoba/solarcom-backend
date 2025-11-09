package com.solarcom.sites_management.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solarcom.sites_management.domain.Element;
import com.solarcom.sites_management.domain.ElementHistory;
import com.solarcom.sites_management.domain.Site;
import com.solarcom.sites_management.domain.SiteTemplate;
import com.solarcom.sites_management.repo.ElementHistoryRepository;
import com.solarcom.sites_management.repo.ElementRepository;
import com.solarcom.sites_management.repo.SiteRepo;
import com.solarcom.sites_management.repo.SiteTemplateRepo;
import com.solarcom.sites_management.services.SiteService;
import com.solarcom.sites_management.web.response.ErrorResponse;

@RestController
@RequestMapping("/api/infrastructure/site")
public class SiteController {
	private static final Logger logger = LoggerFactory.getLogger(SiteTemplateController.class);

	private final SiteRepo siteRepo;
	private final SiteTemplateRepo siteTemplateRepo;
	private final SiteService siteService;
	private final ElementHistoryRepository elementHistoryRepository;
	private final ElementRepository elementRepository;

	public SiteController(SiteRepo siteRepo, SiteTemplateRepo siteTemplateRepo, SiteService siteService,
			ElementHistoryRepository elementHistoryRepository, ElementRepository elementRepository) {
		this.siteRepo = siteRepo;
		this.siteTemplateRepo = siteTemplateRepo;
		this.siteService = siteService;
		this.elementHistoryRepository = elementHistoryRepository;
		this.elementRepository = elementRepository;
	}

	@PostMapping("/addSite")
	public void addSite() {

	}

	@GetMapping("/sites")
	public Iterable<Site> getSites() {
		return this.siteRepo.findAll((PageRequest.of(0, 10)));
	}

	@GetMapping("/{id}")
	Site getSite(@PathVariable("id") String siteId) {
		return this.siteRepo.findById(siteId).get();
	}

	@PostMapping("/create-from-template/{templateId}")
	public ResponseEntity<String> createSiteFromTemplate(@PathVariable("templateId") String templateId,
			@RequestBody Site site) {
		try {

			SiteTemplate template = siteTemplateRepo.findById(templateId).get();
			boolean found = siteRepo.existsBySiteNameAndSiteTemplate(site.getSiteName(), template);
			System.out.println("templlate name =" + template.getTemplateName());
			System.out.println("site name =" + site.getSiteName());
			System.out.println("found  =" + found);
			if (!found) {
				site.setSiteTemplate(template);
				siteRepo.save(site);
				return new ResponseEntity<>("Site created successfully", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Site name already exists", HttpStatus.CONFLICT);
			}

		} catch (Exception e) {
			return new ResponseEntity<>("Failed to create site", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}



	@PutMapping("/update-from-template")
	public ResponseEntity<String> updateSiteFromTemplate(@RequestBody Site updatedSite) {
		try {

			// Retrieve the SiteTemplate from the repository
			SiteTemplate template = siteTemplateRepo.findById(updatedSite.getSiteTemplate().getId())
					.orElseThrow(() -> new RuntimeException("SiteTemplate not found"));

			// Check if the site with the given name and template already exists
			boolean found = siteRepo.existsBySiteNameAndSiteTemplate(updatedSite.getSiteName().trim(), template);
			Site existingSite = siteRepo.findBySiteNameAndSiteTemplate(updatedSite.getSiteName().trim(), template);

			if (found && existingSite.getId().equals(updatedSite.getId())) {
				// Update site properties and handle elements
				updateSite(existingSite, updatedSite);
				siteRepo.save(existingSite); // Save updated site

				// Update elements and save any changes
				if (existingSite.getElements() != null) {
					for (Element element : existingSite.getElements()) {
						elementRepository.save(element); // Save each updated element
					}
				}

				return new ResponseEntity<>("Site updated successfully", HttpStatus.OK);
			} else if (!found) {
				// New site creation
				siteRepo.save(updatedSite); // Save the new site

				// Save new elements if they are not already in the database
				if (updatedSite.getElements() != null) {
					for (Element element : updatedSite.getElements()) {
						if (element.getId() == null || !elementRepository.existsById(element.getId())) {
							element.setCurrentSiteId(updatedSite.getId()); // Set the current site ID for new elements
							elementRepository.save(element); // Save the new element
						}
					}
				}

				return new ResponseEntity<>("Site created successfully", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Site name already exists", HttpStatus.CONFLICT);
			}

		} catch (Exception e) {
			e.printStackTrace(); // Log the exception for debugging
			return new ResponseEntity<>("Failed to update site", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Update the existing site with the information from the updated site. This
	 * method also manages element history.
	 */
	private void updateSite(Site existingSite, Site updatedSite) {
		// Update site properties
		existingSite.setSiteName(updatedSite.getSiteName());
		existingSite.setValues(updatedSite.getValues());

		// Handle elements
		updateElementAssociations(existingSite, updatedSite);
	}

	/**
	 * Update the associations between site and elements. This method also tracks
	 * the history of element site changes.
	 */
	private void updateElementAssociations(Site existingSite, Site updatedSite) {
		// Create a map of element ID to element for easy lookup
		Map<String, Element> updatedElementMap = (updatedSite.getElements() != null)
				? updatedSite.getElements().stream().collect(Collectors.toMap(Element::getId, Function.identity()))
				: Collections.emptyMap();

		// Update existing elements and record history
		if (existingSite.getElements() != null) {
			List<Element> elementToRemove = null;
			for (Element existingElement : existingSite.getElements()) {
				Element updatedElement = updatedElementMap.get(existingElement.getId());

				if (updatedElement != null) {
					if (!existingElement.getCurrentSiteId().equals(updatedSite.getId())) {
						// Record history of the site change
						ElementHistory history = new ElementHistory();
						history.setElementId(existingElement.getId());
						history.setPreviousSiteId(existingElement.getCurrentSiteId());
						history.setNewSiteId(updatedSite.getId());
						history.setChangeDate(LocalDateTime.now());
						elementHistoryRepository.save(history);

						// Update element with new site ID
						existingElement.setCurrentSiteId(updatedSite.getId());
					}
				} else {
					// we remove the element that is not in updatedSite
					// Record history of the site change
					ElementHistory history = new ElementHistory();
					history.setElementId(existingElement.getId());
					history.setPreviousSiteId(existingElement.getCurrentSiteId());
					history.setNewSiteId(null);
					history.setChangeDate(LocalDateTime.now());
					elementHistoryRepository.save(history);
					// Update element with new site ID
					existingElement.setCurrentSiteId(null);
					if (elementToRemove == null) {
						elementToRemove = new ArrayList<Element>();
					}
					elementToRemove.add(existingElement);
					elementRepository.save(existingElement);

				}
			}
			if (elementToRemove!=null && !elementToRemove.isEmpty()) {
				existingSite.getElements().removeAll(elementToRemove);
			}
		}

		// Add new elements that are not yet in the existing elements list
		if (updatedSite.getElements() != null) {
			for (Element newElement : updatedSite.getElements()) {
				if (existingSite.getElements() == null
						|| existingSite.getElements().stream().noneMatch(e -> e.getId().equals(newElement.getId()))) {
					newElement.setCurrentSiteId(updatedSite.getId());
					// Record history of the site change
					ElementHistory history = new ElementHistory();
					history.setElementId(newElement.getId());
					history.setPreviousSiteId(newElement.getCurrentSiteId());
					history.setNewSiteId(updatedSite.getId());
					history.setChangeDate(LocalDateTime.now());
					elementHistoryRepository.save(history);

					if (existingSite.getElements() == null) {
						existingSite.setElements(new ArrayList<>()); // Initialize if null
					}
					existingSite.getElements().add(newElement);
				}
			}
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllSites() {
		try {

			List<Site> sites = siteRepo.findAll();

			return new ResponseEntity<>(sites, HttpStatus.OK);
		} catch (Exception e) {
			// Log the error
			logger.error("Error fetching templates", e);

			// Return a detailed error response
			return new ResponseEntity<>(new ErrorResponse("Failed to fetch sites", e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/all/{templateId}")
	public List<Site> getSitesByTemplate(@PathVariable("templateId") String templateId) {
		System.out.println("templateId  = " + templateId);
		return siteService.getSitesByTemplate(templateId);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSite(@PathVariable("id") String id) {
		// Check if the element exists
		if (!siteRepo.existsById(id)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Site with ID " + id + " not found.");
		}

		try {
			// Delete the element
			siteRepo.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content
		} catch (Exception e) {
			// Handle any other errors
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while deleting the element.");
		}
	}
}
