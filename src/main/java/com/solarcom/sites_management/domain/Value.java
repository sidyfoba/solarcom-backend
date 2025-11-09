package com.solarcom.sites_management.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Value {
	private String key;
	private String value;
	
	private String valueType; // image | video | int | string | boolean | double
	private String HtmlType; // inputText | dropdown

}
