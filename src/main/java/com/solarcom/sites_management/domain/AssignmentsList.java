package com.solarcom.sites_management.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "assignments_list")
@NoArgsConstructor
@Getter
@Setter
public class AssignmentsList {
	@Id
	private String id;
	private List<Assignment> assignments;


}
