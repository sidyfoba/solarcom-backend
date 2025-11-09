package com.solarcom.sites_management.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "keysValues") // Specify your MongoDB collection name@Document
public class KeyValue {
	@Id
	private String id;
	private String key;
	private String valueType;
	private String inputComponentType; // TextField, CheckBox, Select
	private String value;
}
