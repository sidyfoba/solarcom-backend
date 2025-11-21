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
@Document(collection = "elements")
public class Element {
	@Id
	private String id;
	private String elementName;
	private String elementId;
	private ElementTemplate elementTemplate; 
	private List<Value> values;
	 private String currentSiteId; // Reference to the current Site
}
