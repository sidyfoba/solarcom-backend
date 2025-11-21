package com.slcm.solarcom.sites_management.domain.field;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Field {
	  private String name;
	  private String value; // default value if number , string
	  private String priorityValue;
	  private int slaValue;
	    private String type; // dateRange / Text / Editor
	    private List<String> options;
	    private boolean required;
	    private DateRange dateRange; // Optional property
	    private List<Sla> slas;
	    private Editor editor;
	    private Text text;
	    
	    
}
