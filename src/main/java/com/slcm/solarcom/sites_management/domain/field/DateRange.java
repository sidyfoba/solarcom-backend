package com.slcm.solarcom.sites_management.domain.field;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class DateRange {
	
	private String startName;
	private String startDateTime; // Consider using LocalDateTime or Date
	private String endName;
	private String endDateTime; // Consider using LocalDateTime or Date
	// the duration is bind to starttime and endtime so we can calculate the
	// difference
	// the calculate the duration if starttime is not null
	// the default value is 0 hours
	private String duration;

	// we put the SLA in here so that we can handle the duration and to know if we
	// are in or out of SLA
	private Sla sla;

}
