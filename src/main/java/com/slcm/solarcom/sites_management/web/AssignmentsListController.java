package com.slcm.solarcom.sites_management.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.slcm.sites_management.domain.Assignment;
import com.slcm.sites_management.domain.AssignmentsList;
import com.slcm.sites_management.domain.Update;
import com.slcm.sites_management.domain.UpdateMessages;
import com.slcm.solarcom.sites_management.services.AssignmentsService;
import com.slcm.solarcom.sites_management.services.UpdateMessagesService;

@RestController
@RequestMapping("/api/process/assignments-list")
public class AssignmentsListController {

	 @Autowired
	    private AssignmentsService service;

	    // Create or get UpdateMessages
	    @PostMapping("/create")
	    public AssignmentsList createUpdateMessages(@RequestBody AssignmentsList assignmentsList) {
	        return service.saveAssignmentsList(assignmentsList);
	    }

	    // Add Update to a message
	    @PostMapping("/{id}/add/update")
	    public AssignmentsList addAssignment(@PathVariable String id, @RequestBody Assignment assignment) {
	        return service.addAssignment(id, assignment);
	    }

	    // Get all updates from a message
	    @GetMapping("/{id}/assignments")
	    public AssignmentsList getAssignments(@PathVariable("id") String id) {
	        return service.getAssignments(id);
	    }
}
