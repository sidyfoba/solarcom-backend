package com.slcm.sites_management.domain;

import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "sites") // Specify your MongoDB collection name@Document
public class Site {
	@Id
	private String id;
	private String siteName;
	private String siteId;
	private SiteTemplate siteTemplate; // the contains the memory fo what kind of data we have to store for a site
	private List<Value> values;// that is the effective data
//	private Map<String, Object> data; // legacy
	@DBRef
	private List<Element> elements; // List of references to Element documents

}
