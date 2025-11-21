package com.slcm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.slcm.solarcom.sites_management.repo.SiteRepo;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.solarcom.sites_management",
        "com.slcm"        // <-- include this root
})
public class SitesManagementApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(SitesManagementApplication.class);

	private final SiteRepo siteRepo;

	public SitesManagementApplication(SiteRepo siteRepo) {
		this.siteRepo = siteRepo;
	}

	public static void main(String[] args) {
		SpringApplication.run(SitesManagementApplication.class, args);
		logger.info("Application started");
	}

	@Override
	public void run(String... args) throws Exception {
//		Site site1 = new Site();
//		site1.setSiteName("site 1");
//		site1.setProperties(new ArrayList<Property>());
//		for (int i = 0; i < 10; i++) {
//			Property property = new Property();
//			property.setKey("att " + i);
//			property.setValue("val " + i);
//			site1.getProperties().add(property);
//
//		}
//		this.siteRepo.save(site1);
//
//// Fetch all cars and log to console
//		for (Site site : siteRepo.findAll()) {
//			logger.info("site name :", site.getSiteName());
//		}
	}
	
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//	    return new BCryptPasswordEncoder();
//	}


}
