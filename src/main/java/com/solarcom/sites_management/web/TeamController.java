package com.solarcom.sites_management.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.solarcom.sites_management.domain.Team;
import com.solarcom.sites_management.repo.EmployeeRepository;
import com.solarcom.sites_management.repo.TeamRepository;

@RestController
@RequestMapping("/api/hr/teams")
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Team> addTeam(@RequestBody Team team) {
        Team savedTeam = teamRepository.save(team);
        return new ResponseEntity<>(savedTeam, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable("id") String id, @RequestBody Team teamDetails) {
        Optional<Team> optionalTeam = teamRepository.findById(id);
        
        if (!optionalTeam.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Team teamToUpdate = optionalTeam.get();
        teamToUpdate.setName(teamDetails.getName());
        teamToUpdate.setResponsibilities(teamDetails.getResponsibilities());
        teamToUpdate.setTeamLeaderID(teamDetails.getTeamLeaderID());
        teamToUpdate.setMemberIDs(teamDetails.getMemberIDs());

        Team updatedTeam = teamRepository.save(teamToUpdate);
        return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable("id") String id) {
    	 teamRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
