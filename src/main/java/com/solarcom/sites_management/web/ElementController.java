package com.solarcom.sites_management.web;

import java.util.List;

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
import com.solarcom.sites_management.domain.ElementTemplate;
import com.solarcom.sites_management.domain.Site;
import com.solarcom.sites_management.domain.SiteTemplate;
import com.solarcom.sites_management.repo.ElementRepository;
import com.solarcom.sites_management.repo.ElementTemplateRepository;
import com.solarcom.sites_management.services.SiteService;
import com.solarcom.sites_management.web.response.ErrorResponse;

@RestController
@RequestMapping("/api/infrastructure/element")
public class ElementController {
	private static final Logger logger = LoggerFactory.getLogger(SiteTemplateController.class);

	private final ElementRepository elementRepository;
	private final ElementTemplateRepository elementTemplateRepository;
	private final SiteService siteService;

	public ElementController(ElementRepository elementRepository, ElementTemplateRepository elementTemplateRepository,
			SiteService siteService) {
		this.elementRepository = elementRepository;
		this.elementTemplateRepository = elementTemplateRepository;
		this.siteService = siteService;
	}

	@PostMapping("/addElement")
	public void addElement() {

	}

	@GetMapping("/elements")
	public Iterable<Element> getElements() {
		return this.elementRepository.findAll((PageRequest.of(0, 10)));
	}

	@GetMapping("/{id}")
	Element getElement(@PathVariable("id") String id) {
//		if (!elementRepository.existsById(id)) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Element with ID " + id + " not found.");
//		}
		return this.elementRepository.findById(id).get();
	}

	@PostMapping("/create-from-template/{templateId}")
	public ResponseEntity<String> creatElementFromTemplate(@PathVariable("templateId") String templateId,
			@RequestBody Element element) {
		try {

			ElementTemplate template = elementTemplateRepository.findById(templateId).get();
			boolean found = elementRepository.existsByElementNameAndElementTemplate(element.getElementName(), template);
			if (!found) {
				element.setElementTemplate(template);
				elementRepository.save(element);
				return new ResponseEntity<>("Site created successfully", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Site name already exists", HttpStatus.CONFLICT);
			}

		} catch (Exception e) {
			return new ResponseEntity<>("Failed to create element", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update-from-template")
	public ResponseEntity<String> updateElementFromTemplate(@RequestBody Element element) {
		try {
//			System.out.println("element function");

			ElementTemplate template = elementTemplateRepository.findById(element.getElementTemplate().getId()).get();
			boolean found = elementRepository.existsByElementNameAndElementTemplate(element.getElementName().trim(),
					template);
			Element elementFound = elementRepository
					.findByElementNameAndElementTemplate(element.getElementName().trim(), template);

			if (found && elementFound.getId().equals(element.getId())) {
				elementRepository.save(element);
				return new ResponseEntity<>("Element updated successfully", HttpStatus.CREATED);
			} else if (!found) {
				System.out.println("in the if not found close");
				elementRepository.save(element);
				return new ResponseEntity<>("Element updated successfully", HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Element name already exists", HttpStatus.CONFLICT);

			}

		} catch (Exception e) {
			return new ResponseEntity<>("Failed to create element", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllElements() {
		try {

			List<Element> elements = elementRepository.findAll();

			return new ResponseEntity<>(elements, HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error fetching templates", e);

			// Return a detailed error response
			return new ResponseEntity<>(new ErrorResponse("Failed to fetch sites", e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/all/{templateId}")
	public List<Element> getElementsByTemplate(@PathVariable("templateId") String templateId) {

		List<Element> elements = elementRepository.findByElementTemplateId(templateId);
		return elements;
	}

	@GetMapping("/all/current-id-site-is-null/{templateId}")
	public List<Element> getElementsByTemplateIdAndCurrentSiteIdIsNull(@PathVariable("templateId") String templateId) {

		List<Element> elements = elementRepository.findByElementTemplateIdAndCurrentSiteIdIsNull(templateId);
		return elements;
	}

}
