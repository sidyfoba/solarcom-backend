package com.slcm.solarcom.sites_management.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.slcm.sites_management.domain.Site;
import com.slcm.sites_management.domain.Ticket;
import com.slcm.sites_management.domain.TicketTemplate;

public interface TicketRepository extends MongoRepository<Ticket, String>, PagingAndSortingRepository<Ticket, String> {

	 // Query method to find sites by siteTemplate ID
    @Query("{ 'ticketTemplate.id': ?0 }")
    List<Ticket> findByTicketTemplateId(String ticketTemplateId);
	 List<Ticket> findByTicketTemplate(TicketTemplate template);
	 boolean existsByTitleAndTicketTemplate(String title, TicketTemplate ticketTemplate);
	 Site findByTitleAndTicketTemplate(String title, TicketTemplate template);
}
