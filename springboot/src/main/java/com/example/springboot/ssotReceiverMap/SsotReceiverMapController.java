package com.example.springboot.ssotReceiverMap;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping (value= "/api/SsotReceiverMap")
public class SsotReceiverMapController {
    
    @Autowired
    private final SsotReceiverMapService ssotReceiverMapService;

    public SsotReceiverMapController(SsotReceiverMapService ssotReceiverMapService) {
        this.ssotReceiverMapService = ssotReceiverMapService;
    }
    
    @GetMapping
    @ResponseBody
    public HashMap<Long,String> getReceiverField(@RequestParam long receiverId) {
        HashMap<Long,String> map = ssotReceiverMapService.getReceiverField(receiverId);
        return map;
    }



}