package com.solarcom.sites_management.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "element_history")
public class ElementHistory {
    @Id
    private String id;
    private String elementId; // Reference to the Element
    private String previousSiteId; // Previous Site (null if this is the first association)
    private String newSiteId; // New Site (null if this is the current association)
    private LocalDateTime changeDate; // When the change occurred
}
