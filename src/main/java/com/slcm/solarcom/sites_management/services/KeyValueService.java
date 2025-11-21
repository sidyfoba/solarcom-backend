package com.slcm.solarcom.sites_management.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slcm.sites_management.domain.KeyValueDocument;
import com.slcm.solarcom.sites_management.repo.KeyValueRepository;

@Service
public class KeyValueService {

    @Autowired
    private KeyValueRepository keyValueRepository;

    public KeyValueDocument updateNewData(String id, Map<String, Object> newData) {
        KeyValueDocument document = keyValueRepository.findById(id)
        		
                .orElseThrow(() -> new RuntimeException("Document not found"));
        System.out.println("the id of the document = "+document.getId());
        
        document.setNewData(newData);
        return keyValueRepository.save(document);
    }
}
