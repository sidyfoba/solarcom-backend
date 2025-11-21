package com.slcm.sites_management.domain;


import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "key_value_collection") // Specify your MongoDB collection name
public class KeyValueDocument {

    @Id
    private String id; // MongoDB ObjectId

    private Map<String, Object> data; // legacy
    private Map<String, Object> newData; // when the alarm is cleared

    

	public Map<String, Object> getNewData() {
		return newData;
	}

	public void setNewData(Map<String, Object> newData) {
		this.newData = newData;
	}

	// Constructors, getters, setters
    public KeyValueDocument() {
    }

    public KeyValueDocument(Map<String, Object> data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}

