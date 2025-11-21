package com.slcm.solarcom.sites_management.services;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import com.slcm.sites_management.domain.Email;
import com.slcm.sites_management.domain.KeyValueDocument;
import com.slcm.solarcom.sites_management.repo.EmailRepository;
import com.slcm.solarcom.sites_management.repo.KeyValueRepository;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private KeyValueRepository keyValueDocumentRepository;
    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
    public void sendEmail2(List<String> to, String subject, String text, boolean isHtml) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // true for multipart

        helper.setTo(to.toArray(new String[0])); // Set multiple recipients
        helper.setSubject(subject);
        helper.setText(text, isHtml); // Set HTML content if isHtml is true

        emailSender.send(message);
        
    }
    public void sendEmail3(List<String> recipients, String subject, String message, String keyValueDocumentId) {
        // Find the existing KeyValueDocument by its ID
        Optional<KeyValueDocument> optionalKeyValueDocument = keyValueDocumentRepository.findById(keyValueDocumentId);
        
        if (!optionalKeyValueDocument.isPresent()) {
            throw new RuntimeException("KeyValueDocument not found with ID: " + keyValueDocumentId);
        }

        // Use the existing KeyValueDocument
        KeyValueDocument keyValueDocument = optionalKeyValueDocument.get();

        // Create and save the Email entity
        Email email = new Email(recipients, subject, message, keyValueDocument);
        try {
			sendEmail2(email.getRecipients(), email.getSubject(), email.getMessage(), true);
			 emailRepository.save(email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        

        // Logic to send the email goes here
    }
    public void sendEmailWithInlineImages(List<String> to, String subject, String text, List<File> inlineImages) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to.toArray(new String[0]));
        helper.setSubject(subject);
        helper.setText(text, true); // Set HTML content

        for (File image : inlineImages) {
            helper.addInline(image.getName(), image);
        }

        emailSender.send(message);
    }
	public List<Email> findByKeyValueDocumentId(String keyValueDocumentId) {
		return emailRepository.findByKeyValueDocumentId(keyValueDocumentId);
	}
}
