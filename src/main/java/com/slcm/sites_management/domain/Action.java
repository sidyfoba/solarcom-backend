package com.slcm.sites_management.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Action {
	private String action;
	  private String status; // "todo" | "done";
	  private LocalDateTime startDate;
	  private LocalDateTime endDate;
}
