package com.slcm.sites_management.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "users") // Specify your MongoDB collection name@Document
public class User {
	@Id
	private String id;
	private String login;
	private String password;
	private String name;
	private String email;
	private List<String > roles;

//	One-to-One Relationship
	@DBRef
	private Profile profile;

}
