package com.slcm.solarcom.sites_management.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.slcm.sites_management.domain.TicketTemplate;
import com.slcm.solarcom.sites_management.repo.TicketTemplateRepository;
import com.slcm.solarcom.sites_management.web.response.ErrorResponse;
import com.slcm.solarcom.sites_management.web.response.TroubleTicketTemplateResponse;

@RestController
@RequestMapping("/api/admin/process/ticket/template")
public class TicketTemplateController {
	private static final Logger logger = LoggerFactory.getLogger(TicketTemplateController.class);

	private final TicketTemplateRepository ticketTemplateRepository;

	public TicketTemplateController(TicketTemplateRepository ticketTemplateRepository) {
		this.ticketTemplateRepository = ticketTemplateRepository;
	}

	@PostMapping("/create")
	public ResponseEntity<?> createTemplate(@RequestBody TicketTemplate ticketTemplate) {
		try {
			// Check if a template with the same name already exists
			TicketTemplate existingTemplate = ticketTemplateRepository
					.findByTemplateName(ticketTemplate.getTemplateName());

			if (existingTemplate == null) {
				// Save the new template if it does not exist
				TicketTemplate createdTemplate = ticketTemplateRepository.save(ticketTemplate);

				// Return a success response with the created template and status code 201
				return new ResponseEntity<>(new TroubleTicketTemplateResponse("Template created successfully", createdTemplate),
						HttpStatus.CREATED);
			} else {
				// Return a response indicating that the template already exists
				return new ResponseEntity<>(
						new ErrorResponse("Template already exists", "A template with this name already exists."),
						HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			// Log the error
			logger.error("Error creating trouble ticket template", e);

			// Return a detailed error response
			return new ResponseEntity<>(new ErrorResponse("Failed to create Trouble ticket template", e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteSiteTemplate(@PathVariable("id") String id) {
		try {
			if (!ticketTemplateRepository.existsById(id)) {
				throw new RuntimeException("Template not found");
			}
			ticketTemplateRepository.deleteById(id);
//        	siteTemplateRepo.deleteTemplateById(id);
			return new ResponseEntity<>("Template deleted successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to delete template", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateSiteTemplate(@PathVariable("id") String id,
			@RequestBody TicketTemplate updatedTemplate) {
		try {
//			siteTemplateService.updateTemplate(id, updatedTemplate);
			if (!ticketTemplateRepository.existsById(id)) {
				throw new RuntimeException("Template not found");
			}
			updatedTemplate.setId(id); // Ensure the ID is set correctly
			ticketTemplateRepository.save(updatedTemplate);
			return new ResponseEntity<>("Template updated successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to update template", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getTicketTemplate(@PathVariable("id") String id) {
		try {
//			siteTemplateService.updateTemplate(id, updatedTemplate);
			if (!ticketTemplateRepository.existsById(id)) {
				throw new RuntimeException("Template not found");
			}
//			updatedTemplate.setId(id); // Ensure the ID is set correctly
			TicketTemplate ticketTemplate = 	ticketTemplateRepository.findById(id).get();
			return new ResponseEntity<>(ticketTemplate, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to load template", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllTemplates() {
		try {

			List<TicketTemplate> templates = ticketTemplateRepository.findAll();
			System.out.println("length of templates = " + templates.size());
			for (TicketTemplate troubleTicketTemplate: templates) {
//					  logger.error("template name  = ", siteTemplate.getId());
				System.err.println(troubleTicketTemplate.getId());
			}

			// Return a success response with the list of templates and status code 200
			TroubleTicketTemplateResponse response = new TroubleTicketTemplateResponse("Templates fetched successfully", templates);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			// Log the error
			logger.error("Error fetching templates", e);

			// Return a detailed error response
			return new ResponseEntity<>(new ErrorResponse("Failed to fetch templates", e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
