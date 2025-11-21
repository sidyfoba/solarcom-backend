package com.slcm.solarcom.sites_management.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.slcm.sites_management.domain.Update;
import com.slcm.sites_management.domain.UpdateMessages;
import com.slcm.solarcom.sites_management.services.UpdateMessagesService;

@RestController
@RequestMapping("/api/process/update/messages")
public class UpdateMessagesController {

    @Autowired
    private UpdateMessagesService service;

    // Create or get UpdateMessages
    @PostMapping("/create")
    public UpdateMessages createUpdateMessages(@RequestBody UpdateMessages updateMessages) {
        return service.saveUpdateMessage(updateMessages);
    }

    // Add Update to a message
    @PostMapping("/{id}/add/update")
    public UpdateMessages addUpdate(@PathVariable String id, @RequestBody Update update) {
        return service.addUpdate(id, update);
    }

    // Get all updates from a message
    @GetMapping("/{id}/updates")
    public UpdateMessages getUpdates(@PathVariable("id") String id) {
        return service.getUpdates(id);
    }
}

