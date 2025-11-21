package com.slcm.sites_management.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "update_messages")
@NoArgsConstructor
@Getter
@Setter
public class UpdateMessages {
	@Id
	private String id;
	private String updatesTitle;
	private List<Update> updates;

}
