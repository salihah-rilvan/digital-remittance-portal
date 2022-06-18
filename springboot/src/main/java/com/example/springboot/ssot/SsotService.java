package com.example.springboot.ssot;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SsotService {
    
    @Autowired
    SsotRepository repository;

    public List<Ssot> findAll(){
        return repository.findAll();
    }

    public String createSsot(String newSsot) { 
        // Check if Ssot name exists
        List<Ssot> list = repository.findAll();
        for (Ssot ssot: list) {
            if (ssot.getSsotName().equals(newSsot)) {
                return "Ssot already exists";
            }
        }
        repository.save(new Ssot(newSsot));
        return "Ssot successfully added";
    }

    // For admin to rename Ssot names
    public void changeSsotNames(Map<String, String> data) {
        List<Ssot> ssotList = findAll();
        for (String key : data.keySet()) {
            for (Ssot ssot : ssotList) {
                if (Long.parseLong(key)  == ssot.getSsotId()) {
                    ssot.setSsotName(data.get(key));
                }
            }
        }
    }

    public void deleteSsot(long ssotId) { 
        repository.deleteSsotBySsotId(ssotId);
    }
}
