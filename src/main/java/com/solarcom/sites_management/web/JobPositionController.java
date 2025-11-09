package com.solarcom.sites_management.web;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solarcom.sites_management.domain.JobPosition;
import com.solarcom.sites_management.services.JobPositionService;

@RestController
@RequestMapping("/api/hr/job-positions")
public class JobPositionController {

 @Autowired
 private JobPositionService jobPositionService;

 @GetMapping
 public List<JobPosition> getAllJobPositions() {
     return jobPositionService.getAllJobPositions();
 }

 @PostMapping
 public JobPosition createJobPosition(@RequestBody JobPosition jobPosition) {
     return jobPositionService.saveJobPosition(jobPosition);
 }

 @PutMapping("/{id}")
 public ResponseEntity<JobPosition> updateJobPosition(
         @PathVariable("id") String id, @RequestBody JobPosition jobPosition) {
     jobPosition.setId(id);
     JobPosition updatedPosition = jobPositionService.saveJobPosition(jobPosition);
     return ResponseEntity.ok(updatedPosition);
 }

 @DeleteMapping("/{id}")
 public ResponseEntity<Void> deleteJobPosition(@PathVariable("id") String id) {
     jobPositionService.deleteJobPosition(id);
     return ResponseEntity.noContent().build();
 }
}
