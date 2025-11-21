package com.slcm.sites_management.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//activity
@Document(collection = "tasks")
@NoArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private String status;// completed / pending (reason) / not-started / in-progress / blockage (reason)
    //the dueDate must always be a future date
    private LocalDate dueDate; //is the normal date which is fixe as reference to know if the user has delay or not
    private Recurrence recurrence; // Optional enum for recurrence
    private Integer recurrenceValue; // Optional value for days, weeks, or months
    private LocalDate nextDueDate; // For scheduling the next instance
    private String parentTaskId; // Reference to the parent task if this is a recurring task
    private String childTaskId;
    
    //time management
    //dueDate
    private LocalDateTime createAt;
    //these two filds are important cause it is used to create the timeline calendar
    private LocalDateTime completedAt; //start time & end time
//    private LocalDateTime start; // when in progress is chosen
//    private LocalDateTime end;
//prend en compte    
    private List<Action> actions;
    
   
    
    
    private String ticketId; // Reference to Ticket's ID
    private String assignedTo; // Reference to User's ID

    // Getters and setters

    // Constructors

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
