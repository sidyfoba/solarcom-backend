package com.solarcom.sites_management.web.response;

import com.solarcom.sites_management.domain.ElementTemplate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponseElementtTemplate {

	   private String message;
	    private ElementTemplate data;
	  

	    public SuccessResponseElementtTemplate(String message, ElementTemplate data) {
	        this.message = message;
	        this.data = data;
	    }
//	    public SuccessResponseElemetTemplate(String message, List<SiteTemplate> datas) {
//	        this.message = message;
//	        this.setDatas(datas);
//	    }

}
