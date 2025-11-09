package com.solarcom.sites_management.web.request;

import com.solarcom.sites_management.domain.AssignmentsList;
import com.solarcom.sites_management.domain.Ticket;
import com.solarcom.sites_management.domain.UpdateMessages;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class TicketRequest {
	private Ticket ticket;
private UpdateMessages updateMessages;
private AssignmentsList assignmentsList;
}
