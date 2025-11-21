package com.slcm.solarcom.sites_management.web;



import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.slcm.sites_management.domain.Email;
import com.slcm.sites_management.domain.EmailRequest;
import com.slcm.sites_management.domain.KeyValueDocument;
import com.slcm.solarcom.sites_management.repo.KeyValueRepository;
import com.slcm.solarcom.sites_management.services.EmailService;
import com.slcm.solarcom.sites_management.services.KeyValueService;

@RestController
@RequestMapping("/api")
public class KeyValueController {

    @Autowired
    private KeyValueRepository keyValueRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private KeyValueService keyValueService;

    // Endpoint to save a key-value pair
    @PostMapping("/keyvalue")
    public KeyValueDocument saveKeyValue(@RequestBody KeyValueDocument keyValue) {
    	System.out.println("key value data  = "+keyValue.getData());

        return keyValueRepository.save(keyValue);
        
    }

    // Endpoint to fetch all key-value pairs (optional, for testing)
    @GetMapping("/getkeyvalue")
    public List<KeyValueDocument> getAllKeyValuePairs() {
        return keyValueRepository.findAll();
    }
    
    @GetMapping("/alarm/{alarmId}")
    public ResponseEntity<KeyValueDocument> getKeyValueById(@PathVariable("alarmId") String id) {
        Optional<KeyValueDocument> document = keyValueRepository.findById(id);
        return document.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/mail/{alarmId}")
    public ResponseEntity<?> updateRow(@PathVariable("alarmId") String id, @RequestBody Map<String, Object> data) {
        // Your update logic here

        // Send email notification
        emailService.sendEmail("sidyfoba@gmail.com", "Alarm Updated", "The alarm with ID " + id + " has been updated.");
        
        return ResponseEntity.ok().build();
    }
    
    // Endpoint to send an email
    @PostMapping("emails/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        try {
            emailService.sendEmail3(
                emailRequest.getRecipients(),
                emailRequest.getSubject(),
                emailRequest.getMessage(),
                emailRequest.getKeyValueDocumentId()
            );
            return new ResponseEntity<>("Email sent successfully!", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/emails")
    public ResponseEntity<List<Email>> getEmailsByKeyValueDocumentId(@RequestParam("keyValueDocumentId") String keyValueDocumentId) {
        List<Email> emails = emailService.findByKeyValueDocumentId(keyValueDocumentId);
        return ResponseEntity.ok(emails);
    }
    @PutMapping("/alarm")
    public KeyValueDocument updateAlarmByKeyValueDocumentId(@RequestBody KeyValueDocument keyValue) {
    	return keyValueRepository.save(keyValue);
    }
    
    @PutMapping("/{keyValueDocumentId}/newdata")
    public ResponseEntity<KeyValueDocument> updateNewData(@PathVariable("keyValueDocumentId") String id, @RequestBody Map<String, Object> newData) {
        KeyValueDocument updatedDocument = keyValueService.updateNewData(id, newData);
        return ResponseEntity.ok(updatedDocument);
    }
//    @PostMapping("/send-email")
//    public void sendEmail(@RequestBody EmailRequest emailRequest) throws Exception {
//        emailService.sendEmail2(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText(), true);
//    }
    
//  @PostMapping
//  public ResponseEntity<KeyValueDocument> saveKeyValue(@RequestBody KeyValueDocument keyValue) {
//      KeyValueDocument savedKeyValue = keyValueRepository.save(keyValue);
//      return new ResponseEntity<>(savedKeyValue, HttpStatus.CREATED);
//  }

//  @GetMapping
//  public ResponseEntity<List<KeyValueDocument>> getAllKeyValuePairs() {
//      List<KeyValueDocument> keyValueDocuments = keyValueRepository.findAll();
//      return new ResponseEntity<>(keyValueDocuments, HttpStatus.OK);
//  }
}

