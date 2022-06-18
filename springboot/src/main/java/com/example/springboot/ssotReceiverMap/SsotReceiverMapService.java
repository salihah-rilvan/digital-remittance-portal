package com.example.springboot.ssotReceiverMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SsotReceiverMapService {

    @Autowired
    private SsotReceiverMapRepository ssotReceiverMapRepository;

    public SsotReceiverMapService(SsotReceiverMapRepository ssotReceiverMapRepository){
        this.ssotReceiverMapRepository = ssotReceiverMapRepository;
    }

    // Returns HashMap of Ssot ID : receiver field name
    public HashMap<Long,String> getReceiverField(long receiverId) {
        Collection<SsotReceiverMap> map =  ssotReceiverMapRepository.findReceiverById(receiverId);
        HashMap<Long, String> SsotReceiverMap = new HashMap<Long, String>();
        for (SsotReceiverMap mapObj : map) {
            SsotReceiverMap.put(mapObj.getSsotId(), mapObj.getReceiverFieldName());
        }
        return SsotReceiverMap;
    }   

    public void deleteSsotBySsotId(long ssotId) {
        ssotReceiverMapRepository.deleteBySsotId(ssotId);
    }

    
}