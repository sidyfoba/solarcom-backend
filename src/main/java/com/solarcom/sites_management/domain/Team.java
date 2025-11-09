package com.solarcom.sites_management.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "teams")
public class Team {
	@Id
	private String id;
	private String name;
	private List<String> responsibilities;
	private String teamLeaderID;
	private List<String> memberIDs;

}
