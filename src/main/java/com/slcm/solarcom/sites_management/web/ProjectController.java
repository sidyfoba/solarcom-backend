package com.slcm.solarcom.sites_management.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.slcm.sites_management.domain.Project;
import com.slcm.solarcom.sites_management.repo.ProjectRepository;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

	@Autowired
	private ProjectRepository projectRepository;

	@PostMapping("/create")
	public ResponseEntity<?> createProject(@RequestBody Project project) {
		Project savedProject;
		try {
			savedProject = projectRepository.save(project);
			return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/{projectId}")
	public ResponseEntity<Project> getProjectById(@PathVariable("projectId") String projectId) {
		try {
			System.out.println("id  = "+projectId);
			Project project = projectRepository.findById(projectId)
					.orElseThrow(() -> new RuntimeException("Project not found with id " + projectId));
			return ResponseEntity.ok(project);
		} catch (RuntimeException e) {
			return ResponseEntity.status(404).body(null); // Or handle as appropriate
		}
	}

	@GetMapping("/all")
	public List<Project> getAllProjects() {
		return projectRepository.findAll();
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Project> updateProject(@PathVariable("id") String id, @RequestBody Project projectDetails) {
		try {
			Project updatedProject = projectRepository.findById(id).get();
			updatedProject.setEndDate(projectDetails.getEndDate());
			updatedProject.setProjectDescription(projectDetails.getProjectDescription());
			updatedProject.setProjectManager(projectDetails.getProjectManager());
			updatedProject.setProjectName(projectDetails.getProjectName());
			updatedProject.setStartDate(projectDetails.getStartDate());
			updatedProject.setStatus(projectDetails.getStatus());

			return ResponseEntity.ok(updatedProject);
		} catch (RuntimeException e) {
			return ResponseEntity.status(404).body(null); // Or handle as appropriate
		}
	}
	// Add other endpoints as needed
}
