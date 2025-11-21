package com.slcm.solarcom.sites_management.domain.field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Sla {
	private String priorityLabel; // criticity or priority it depends on the context
	private int sla; // sla in hours

}
