package com.solarcom.sites_management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solarcom.sites_management.domain.Assignment;
import com.solarcom.sites_management.domain.AssignmentsList;
import com.solarcom.sites_management.repo.AssignmentsListRepository;

@Service
public class AssignmentsService {
	@Autowired
	private AssignmentsListRepository assignmentsListRepository;
	
	public AssignmentsList saveAssignmentsList(AssignmentsList assignmentsList) {
        return assignmentsListRepository.save(assignmentsList);
    }

    public AssignmentsList addAssignment(String assignmentsListId, Assignment assignment) {
    	AssignmentsList assignmentsList = assignmentsListRepository.findById(assignmentsListId).orElseThrow(() -> new RuntimeException("Message not found"));
    	assignmentsList.getAssignments().add(assignment);
        return assignmentsListRepository.save(assignmentsList);
    }

    // Optionally, you can also fetch updates
    public  AssignmentsList getAssignments(String assignmentsListId) {
    	return assignmentsListRepository.findById(assignmentsListId)
    	        .orElseThrow(() -> new RuntimeException("Message not found"));

    }

}
