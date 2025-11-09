package com.solarcom.sites_management.domain;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class ElementField {

	private String name; // Property name or key
    private String type; // Property type (e.g., String, Integer, Date, etc.)
    //if type is select then 
   
    private List<String> options;
    private String description; // Optional description of the property
    private boolean required; //if the input is required
    // Add more fields if necessary, like constraints or default values
}
