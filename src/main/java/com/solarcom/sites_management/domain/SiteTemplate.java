package com.solarcom.sites_management.domain;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Memoire pour garder , les columns names et leurs types donc a chaque fois
// qu'un site doit etre creer on va utliser le SiteConfig pour construire le formulaire
// s'il s'agit de  fichier excel, on va voir les entetes du fichier pour recuperer les donnees corespondants

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "sites_template")
public class SiteTemplate {

	@Id
	private String id;
	private String templateName; // the name of the configuration for exemple for site_config_free or
									// site_config_orange
	private List<SiteField> fields;
	private String description;
	private boolean active;

}
