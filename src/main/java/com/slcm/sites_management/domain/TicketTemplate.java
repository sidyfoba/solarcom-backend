package com.slcm.sites_management.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.slcm.solarcom.sites_management.domain.field.Field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "tickets_templates")
public class TicketTemplate {

	@Id
	private String id;
	private String templateName; 
	private List<Field> fields;
	private String description;
	private boolean active;
	private String icon;
}
