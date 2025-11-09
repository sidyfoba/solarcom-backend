package com.solarcom.sites_management.web.response;

import java.util.List;

import com.solarcom.sites_management.domain.SiteTemplate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponse {
    private String message;
    private SiteTemplate data;
    private List<SiteTemplate> datas;

    public SuccessResponse(String message, SiteTemplate data) {
        this.message = message;
        this.data = data;
    }
    public SuccessResponse(String message, List<SiteTemplate> datas) {
        this.message = message;
        this.setDatas(datas);
    }

   
}