package com.slcm.solarcom.sites_management.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slcm.sites_management.domain.Update;
import com.slcm.sites_management.domain.UpdateMessages;
import com.slcm.solarcom.sites_management.repo.UpdateMessagesRepository;

@Service
public class UpdateMessagesService {

    @Autowired
    private UpdateMessagesRepository repository;

    public UpdateMessages saveUpdateMessage(UpdateMessages updateMessages) {
        return repository.save(updateMessages);
    }

    public UpdateMessages addUpdate(String updateMessageId, Update update) {
        UpdateMessages updateMessages = repository.findById(updateMessageId).orElseThrow(() -> new RuntimeException("Message not found"));
        updateMessages.getUpdates().add(update);
        return repository.save(updateMessages);
    }

    // Optionally, you can also fetch updates
    public  UpdateMessages getUpdates(String updateMessageId) {
    	return repository.findById(updateMessageId)
    	        .orElseThrow(() -> new RuntimeException("Message not found"));

    }
}
