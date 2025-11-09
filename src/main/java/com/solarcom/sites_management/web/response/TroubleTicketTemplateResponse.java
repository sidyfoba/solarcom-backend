package com.solarcom.sites_management.web.response;

import java.util.List;

import com.solarcom.sites_management.domain.TicketTemplate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TroubleTicketTemplateResponse {

	private String message;
	
	private TicketTemplate template;
	private List<TicketTemplate> templates;

	public TroubleTicketTemplateResponse(String message, TicketTemplate template) {
		this.message = message;
		this.template = template;
	}


	public TroubleTicketTemplateResponse(String message, List<TicketTemplate> templates) {
		this.message = message;
		this.setTemplates(templates);
	}
}
