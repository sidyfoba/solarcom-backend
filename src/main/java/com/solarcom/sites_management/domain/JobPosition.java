package com.solarcom.sites_management.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "job_positions")
public class JobPosition {

	@Id
	private String id;

	private String title;
	private String description;

}
