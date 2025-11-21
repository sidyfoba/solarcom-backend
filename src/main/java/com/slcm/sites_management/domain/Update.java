package com.slcm.sites_management.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Update {
	private String id;
	private String text;
	private LocalDateTime date;
	private int updateNumber;

}
