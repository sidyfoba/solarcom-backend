package com.solarcom.sites_management.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "emails") // Specify your MongoDB collection name
public class Email {

    @Id
    private String id; // MongoDB ObjectId

    private List<String> recipients; // List of email recipients
    private String subject; // Email subject
    private String message; // Email message content

    @DBRef
    private KeyValueDocument keyValueDocument; // Reference to KeyValueDocument

    // Constructors, getters, setters
    public Email() {
    }

    public Email(List<String> recipients, String subject, String message, KeyValueDocument keyValueDocument) {
        this.recipients = recipients;
        this.subject = subject;
        this.message = message;
        this.keyValueDocument = keyValueDocument;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public KeyValueDocument getKeyValueDocument() {
        return keyValueDocument;
    }

    public void setKeyValueDocument(KeyValueDocument keyValueDocument) {
        this.keyValueDocument = keyValueDocument;
    }
}

