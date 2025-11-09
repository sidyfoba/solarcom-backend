package com.solarcom.sites_management.web;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.solarcom.sites_management.domain.AssignmentsList;
import com.solarcom.sites_management.domain.Site;
import com.solarcom.sites_management.domain.SiteTemplate;
import com.solarcom.sites_management.domain.Ticket;
import com.solarcom.sites_management.domain.TicketTemplate;
import com.solarcom.sites_management.domain.UpdateMessages;
import com.solarcom.sites_management.repo.AssignmentsListRepository;
import com.solarcom.sites_management.repo.SiteRepo;
import com.solarcom.sites_management.repo.TicketRepository;
import com.solarcom.sites_management.repo.TicketTemplateRepository;
import com.solarcom.sites_management.repo.UpdateMessagesRepository;
import com.solarcom.sites_management.web.request.TicketRequest;

@RestController
@RequestMapping("/api/process/tickets")
public class TicketController {
	private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

	private final TicketTemplateRepository templateRepository;
	private final TicketRepository ticketRepository;
	private final UpdateMessagesRepository updateMessagesRepository;
	private final AssignmentsListRepository assignmentsListRepository;

	public TicketController(TicketTemplateRepository templateRepository, TicketRepository ticketRepository,
			UpdateMessagesRepository updateMessagesRepository,AssignmentsListRepository assignmentsListRepository) {
		this.templateRepository = templateRepository;
		this.ticketRepository = ticketRepository;
		this.updateMessagesRepository = updateMessagesRepository;
		this.assignmentsListRepository= assignmentsListRepository;
	}

	@PostMapping("/create-from-template/{templateId}")
	public ResponseEntity<String> createTicketFromTemplate(@PathVariable("templateId") String templateId,
			@RequestBody TicketRequest ticketRequest) {
		try {
			Ticket ticket = ticketRequest.getTicket();
			UpdateMessages updateMessages = ticketRequest.getUpdateMessages();
			System.out.println("update message = " + updateMessages.getUpdatesTitle());
			TicketTemplate template = templateRepository.findById(templateId).get();

			ticket.setTicketTemplate(template);
			if (updateMessages != null) {
				updateMessages = updateMessagesRepository.save(updateMessages);
			}
			ticket.setUpdateMessagesId(updateMessages.getId());
			ticketRepository.save(ticket);
			return new ResponseEntity<>("Ticket created successfully", HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>("Failed to create site", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateTicket(@PathVariable("id") String id,
			@RequestBody TicketRequest ticketRequest) {
		try {
			// Fetch the ticket to update
			Ticket existingTicket = ticketRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Ticket not found"));

			// Get the updated ticket and update messages from the request
			Ticket ticket = ticketRequest.getTicket();
			
			UpdateMessages updateMessages = ticketRequest.getUpdateMessages();
			AssignmentsList assignmentsList = ticketRequest.getAssignmentsList();

			// If there's an update message, update it or create a new one
			if (updateMessages != null) {
				updateMessages = updateMessagesRepository.save(updateMessages);
				ticket.setUpdateMessagesId(updateMessages.getId());
//				existingTicket.setUpdateMessagesId(ticket.getUpdateMessagesId());
			}
			System.out.println("before assign if");
			if (assignmentsList != null) {
				System.out.println("assigns is not null");
				System.out.println(assignmentsList.getAssignments().get(0));
				assignmentsList = assignmentsListRepository.save(assignmentsList);
				existingTicket.setAssignmentListId(assignmentsList.getId());
			}

			// If template ID is provided in the request, update the ticket's template
			if (existingTicket.getTicketTemplate() != null) {
				TicketTemplate template = templateRepository.findById(existingTicket.getTicketTemplate().getId())
						.orElseThrow(() -> new RuntimeException("Template not found"));
				existingTicket.setTicketTemplate(template);
			}

			// Update the fields of the existing ticket
			existingTicket.setTitle(ticket.getTitle());
			existingTicket.setDescription(ticket.getDescription());
			existingTicket.setStatus(ticket.getStatus());
			existingTicket.setValues(ticket.getValues());
			existingTicket.setUpdateMessagesId(ticket.getUpdateMessagesId());
//			existingTicket.setTicketTemplate(existingTicket.getTicketTemplate());

			// Save the updated ticket back to the repository
			ticketRepository.save(existingTicket);

			return new ResponseEntity<>("Ticket updated successfully", HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>("Failed to update ticket", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	

	@GetMapping("/all/{templateId}")
	public List<Ticket> getTicketByTemplate(@PathVariable("templateId") String templateId) {
		System.out.println("templateId  = " + templateId);
		TicketTemplate template = templateRepository.findById(templateId).get();

		List<Ticket> tickets = ticketRepository.findByTicketTemplateId(templateId);
		return tickets;
	}

	@PostMapping
	public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
		Ticket savedTicket = ticketRepository.save(ticket);
		return new ResponseEntity<>(savedTicket, HttpStatus.CREATED);
	}

	@GetMapping
	public List<Ticket> getAllTickets() {
		return ticketRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Ticket> getTicketById(@PathVariable("id") String id) {
		Optional<Ticket> ticket = ticketRepository.findById(id);
		return ticket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Ticket> updateTicket(@PathVariable("id") String id, @RequestBody Ticket ticket) {
		if (!ticketRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		ticket.setId(id);
		Ticket updatedTicket = ticketRepository.save(ticket);
		return ResponseEntity.ok(updatedTicket);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTicket(@PathVariable String id) {
		if (!ticketRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		ticketRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
