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

import com.slcm.sites_management.domain.ElementTemplate;
import com.slcm.solarcom.sites_management.repo.ElementTemplateRepository;
import com.slcm.solarcom.sites_management.web.response.ErrorResponse;
import com.slcm.solarcom.sites_management.web.response.SuccessResponseElementtTemplate;

@RestController
@RequestMapping("/api/admin/infrastructure/element/template")
public class ElementTemplateController {
	private static final Logger logger = LoggerFactory.getLogger(ElementTemplateController.class);
	private final ElementTemplateRepository elementTemplateRepository;


	public ElementTemplateController(ElementTemplateRepository elementTemplateRepository) {
		 this.elementTemplateRepository =elementTemplateRepository; 
	}
	@PostMapping("/create")
	public ResponseEntity<?> createElementTemplate(@RequestBody ElementTemplate elementTemplate) {
	    try {
	        // Check if a template with the same name already exists
	    	ElementTemplate existingTemplate = elementTemplateRepository.findByTemplateName(elementTemplate.getTemplateName());
	        
	        if (existingTemplate == null) {
	            // Save the new template if it does not exist
	            ElementTemplate createdTemplate = elementTemplateRepository.save(elementTemplate);

	            // Return a success response with the created template and status code 201
	            return new ResponseEntity<>(new SuccessResponseElementtTemplate("Template created successfully", createdTemplate),
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
	public ResponseEntity<String> deleteElementTemplate(@PathVariable("id") String id) {
		try {
			if (!elementTemplateRepository.existsById(id)) {
				throw new RuntimeException("Template not found");
			}
			elementTemplateRepository.deleteById(id);
//        	siteTemplateRepo.deleteTemplateById(id);
			return new ResponseEntity<>("Template deleted successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to delete template", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateSiteTemplate(@PathVariable("id") String id,
			@RequestBody ElementTemplate updatedTemplate) {
		try {
//			siteTemplateService.updateTemplate(id, updatedTemplate);
			if (!elementTemplateRepository.existsById(id)) {
				throw new RuntimeException("Template not found");
			}
			updatedTemplate.setId(id); // Ensure the ID is set correctly
			elementTemplateRepository.save(updatedTemplate);
			return new ResponseEntity<>("Template updated successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to update template", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getSiteTemplate(@PathVariable("id") String id) {
		try {
//			siteTemplateService.updateTemplate(id, updatedTemplate);
			if (!elementTemplateRepository.existsById(id)) {
				throw new RuntimeException("Template not found");
			}
//			updatedTemplate.setId(id); // Ensure the ID is set correctly
		ElementTemplate elementTemplate = 	elementTemplateRepository.findById(id).get();
			return new ResponseEntity<>(elementTemplate, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to update template", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	@GetMapping("/all")
	public ResponseEntity<?> getAllTemplates() {
		try {

			List<ElementTemplate> templates = elementTemplateRepository.findAll();
//			System.out.println("length of templates = " + templates.size());
//			for (SiteTemplate siteTemplate : templates) {
////					  logger.error("template name  = ", siteTemplate.getId());
//				System.err.println(siteTemplate.getId());
//			}

			// Return a success response with the list of templates and status code 200
//			SuccessResponse response = new SuccessResponse("Templates fetched successfully", templates);
			return new ResponseEntity<>(templates, HttpStatus.OK);
		} catch (Exception e) {
			// Log the error
			logger.error("Error fetching templates", e);

			// Return a detailed error response
			return new ResponseEntity<>(new ErrorResponse("Failed to fetch templates", e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
