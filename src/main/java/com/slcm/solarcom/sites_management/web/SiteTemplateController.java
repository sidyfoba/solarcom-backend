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

import com.slcm.sites_management.domain.SiteTemplate;
import com.slcm.solarcom.sites_management.repo.SiteTemplateRepo;
import com.slcm.solarcom.sites_management.web.response.ErrorResponse;
import com.slcm.solarcom.sites_management.web.response.SuccessResponse;

@RestController
@RequestMapping("/api/admin/infrastructure/site/template")
public class SiteTemplateController {
	private static final Logger logger = LoggerFactory.getLogger(SiteTemplateController.class);

	private final SiteTemplateRepo siteTemplateRepo;

	public SiteTemplateController(SiteTemplateRepo siteTemplateRepo) {
		this.siteTemplateRepo = siteTemplateRepo;
	}

	@PostMapping("/create")
	public ResponseEntity<?> createSiteTemplate(@RequestBody SiteTemplate siteTemplate) {
	    try {
	        // Check if a template with the same name already exists
	        SiteTemplate existingTemplate = siteTemplateRepo.findByTemplateName(siteTemplate.getTemplateName());
	        
	        if (existingTemplate == null) {
	            // Save the new template if it does not exist
	            SiteTemplate createdTemplate = siteTemplateRepo.save(siteTemplate);

	            // Return a success response with the created template and status code 201
	            return new ResponseEntity<>(new SuccessResponse("Template created successfully", createdTemplate),
	                    HttpStatus.CREATED);
	        } else {
	            // Return a response indicating that the template already exists
	            return new ResponseEntity<>(new ErrorResponse("Template already exists", "A template with this name already exists."),
	                    HttpStatus.CONFLICT);
	        }
	    } catch (Exception e) {
	        // Log the error
	        logger.error("Error creating site template", e);

	        // Return a detailed error response
	        return new ResponseEntity<>(new ErrorResponse("Failed to create site template", e.getMessage()),
	                HttpStatus.BAD_REQUEST);
	    }
	}


	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteSiteTemplate(@PathVariable("id") String id) {
		try {
			if (!siteTemplateRepo.existsById(id)) {
				throw new RuntimeException("Template not found");
			}
			siteTemplateRepo.deleteById(id);
//        	siteTemplateRepo.deleteTemplateById(id);
			return new ResponseEntity<>("Template deleted successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to delete template", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateSiteTemplate(@PathVariable("id") String id,
			@RequestBody SiteTemplate updatedTemplate) {
		try {
//			siteTemplateService.updateTemplate(id, updatedTemplate);
			if (!siteTemplateRepo.existsById(id)) {
				throw new RuntimeException("Template not found");
			}
			updatedTemplate.setId(id); // Ensure the ID is set correctly
			siteTemplateRepo.save(updatedTemplate);
			return new ResponseEntity<>("Template updated successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to update template", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getSiteTemplate(@PathVariable("id") String id) {
		try {
//			siteTemplateService.updateTemplate(id, updatedTemplate);
			if (!siteTemplateRepo.existsById(id)) {
				throw new RuntimeException("Template not found");
			}
//			updatedTemplate.setId(id); // Ensure the ID is set correctly
		SiteTemplate siteTemplate = 	siteTemplateRepo.findById(id).get();
			return new ResponseEntity<>(siteTemplate, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to update template", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllTemplates() {
		try {

			List<SiteTemplate> templates = siteTemplateRepo.findAll();
			System.out.println("length of templates = " + templates.size());
			for (SiteTemplate siteTemplate : templates) {
//					  logger.error("template name  = ", siteTemplate.getId());
				System.err.println(siteTemplate.getId());
			}

			// Return a success response with the list of templates and status code 200
			SuccessResponse response = new SuccessResponse("Templates fetched successfully", templates);
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
