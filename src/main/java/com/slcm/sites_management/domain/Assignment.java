package com.slcm.sites_management.domain;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Assignment {
	private String id;
	private String description; //or message
	private LocalDateTime onAssignDate;
	private LocalDateTime onAcknowledgementDate;
	private LocalDateTime rejectTicketDate;
	private LocalDateTime returnTicketDate;
	private boolean acknowledgement;
	private boolean rejectTicket;
	private boolean returnTicket;
	// user - groupe  - team assignment
	private String teamId;
	
}
