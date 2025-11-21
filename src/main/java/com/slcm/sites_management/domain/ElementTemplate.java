package com.slcm.sites_management.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "elements_template")
public class ElementTemplate {
	@Id
	private String id;
	private String templateName; 
	private List<ElementField> fields;
	private String description;
	private boolean active;
	private String icon;
}
