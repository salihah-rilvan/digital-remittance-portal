package com.example.springboot.ssotSenderMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.springboot.excelHelper.ExcelHelper;

@Service
public class SsotSenderMapService {   
    @Autowired
    private SsotSenderMapRepository ssotSenderMapRepository;

    // Get all the field mapping by sender
    public HashMap<Long,String> getSenderField(long userId) {
        Collection<SsotSenderMap> map =  ssotSenderMapRepository.findAllByUserId(userId);
        HashMap<Long, String> SsotSenderMap = new HashMap<Long, String>();
        for (SsotSenderMap mapObj : map) {
            SsotSenderMap.put(mapObj.getSsotId(), mapObj.getField());
        }
        return SsotSenderMap;
    }

    public void addNewSsotSenderMap(SsotSenderMap ssotSenderMap){
        ssotSenderMapRepository.save(ssotSenderMap);
    };

    // Parse Excel file to list of field names
    public static List<String> parse(MultipartFile file, Map<Long, String> ssots) {
        try {
            List<String> headers = ExcelHelper.excelToHeader(file.getInputStream(), ssots);
            
            return headers;
        } catch (IOException e) {
            throw new RuntimeException("Fail to store Excel data: " + e.getMessage());
        }
    }

    public void save(List<SsotSenderMap> mappings) {
        ssotSenderMapRepository.saveAll(mappings);
    }

    public void deleteByUserId(long userId) {
        ssotSenderMapRepository.deleteBySenderId(userId);
    }

    public void deleteSsotBySsotId(long ssotId) {
        ssotSenderMapRepository.deleteBySsotId(ssotId);
    }
    

}
