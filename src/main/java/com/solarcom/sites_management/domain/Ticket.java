package com.solarcom.sites_management.domain;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.solarcom.sites_management.domain.field.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "tickets")
@NoArgsConstructor
@Getter
@Setter
public class Ticket {
    @Id
    private String id;
    private String ticketId; // number + template code + ticket number
    private String title;
    private String description;
    private String status; // pending / closed  / paused / cancel
    
    @DBRef
    private TicketTemplate ticketTemplate;
    private List<Field> values; // value
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String assignedTo; // Reference to User's ID
    private boolean updatesActivated; // for client notification
    private boolean commentsActivated;// to active comments in internal process
    private String updateMessagesId ;
    private String assignmentListId ;
    
    //
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
