package com.example.springboot.ssotSenderMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import com.example.springboot.excelHelper.ExcelHelper;
import com.example.springboot.ssot.Ssot;
import com.example.springboot.ssot.SsotService;
import com.example.springboot.transaction.helperFunctions.FieldMapRecommendation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;

@CrossOrigin("http://localhost:8080")
@Controller
public class SsotSenderMapController {
    
    @Autowired
    private final SsotSenderMapService ssotSenderMapService;

    public SsotSenderMapController(SsotSenderMapService ssotSenderMapService) {
        this.ssotSenderMapService = ssotSenderMapService;
    }

    @Autowired
    private SsotService ssotService;


    @PostMapping
    public void addNewMapping(@RequestBody SsotSenderMap ssotSenderMap){
        ssotSenderMapService.addNewSsotSenderMap(ssotSenderMap);
    }

    // Get submit field name page
    @RequestMapping(value = { "/header" }, method = RequestMethod.GET) 
    public ModelAndView header(HttpSession session) {
        ModelAndView model = new ModelAndView();  
        long senderId = (Long) session.getAttribute("senderid");

        HashMap<Long, String> prev = ssotSenderMapService.getSenderField(senderId);
        List<Ssot> ssotObjects = ssotService.findAll();

        // Reset session variable "noOfFields" 
        session.setAttribute("noOfFields", prev.size());  

        // Check if there is current field mapping in the database 
        // if yes pass them to front end for user to edit
        if (prev.size()>=1){
            model.addObject("mappings", prev);
            model.addObject("status", true);
            model.addObject("ssots", ssotObjects);
        };
        model.setViewName("header");
        return model;
    }

    // Processing submit Excel file with field name request
    @RequestMapping(value = { "/header" }, method = RequestMethod.POST)
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile file, HttpSession session) {
        ModelAndView model = new ModelAndView();
    
        if (ExcelHelper.hasExcelFormat(file)) {           
            try {
                // Get list of ssots
                List<Ssot> ssotObjects = ssotService.findAll();
                Map<Long, String> ssots = ssotObjects.stream().collect(Collectors.toMap(Ssot::getSsotId, Ssot::getSsotName));
                
                // Parse the Excel file into a list of field name, throw error if number of submitted fields is smaller than
                // number of SSOTs
                List<String> headers = SsotSenderMapService.parse(file, ssots);
                
                // Recommend field mappings to ssots
                HashMap<Long, String> recMapping = FieldMapRecommendation.recommend(ssots, headers);
                
                // Send objects to front end for user to check and make change to field mapping 
                model.addObject("mappings", recMapping);
                model.addObject("msg", "File upload success");
                model.addObject("headers", headers);
                model.addObject("ssots", ssotObjects);
                model.addObject("status", true);
                model.setViewName("header");

            } catch (Exception e) {
                // Send error message to front end
                model.addObject("msg2", e.getMessage());
                model.addObject("status", false);
                model.setViewName("header");
            }
        }else{
            // Send error message to front end (when user not upload an Excel file)
            model.addObject("msg2", "Please upload an Excel file");
            model.addObject("status", false);
            model.setViewName("header");
        }
        return model;
    }

    // Processing field mapping request
    @RequestMapping(value = { "/mapping" }, method = RequestMethod.POST)
    public ModelAndView mapFields(@RequestParam Map<String,String> allRequestParams, HttpSession session)  {       
        long senderId = (Long) session.getAttribute("senderid");      
        int noOfFields = (int) session.getAttribute("noOfFields");
        int noOfSsots = (int) session.getAttribute("noOfSsots");


        // Check if user has mappings in the database
        // if yes, remove from database
        if (noOfFields > 0){
            ssotSenderMapService.deleteByUserId(senderId);
        }

        List<SsotSenderMap> mappings = new ArrayList<>();
        ModelAndView model = new ModelAndView();

        // Create a list of Map Objects and save to database
        for (var key: allRequestParams.keySet()){
            long id = Long.parseLong(key);
            String field = allRequestParams.get(key);
            if (!field.isBlank() && !field.isEmpty()){
                SsotSenderMap mapping = new SsotSenderMap(id, field, senderId);
                mappings.add(mapping);
            }

        }

        if (mappings.size() < noOfSsots){
            model.addObject("msg4", "You didn't map some fields to SSOTs");

        }else{
            ssotSenderMapService.save(mappings);
            // Update noOfFields (for pop-up message in front end) and send success message to front end 
            session.setAttribute("noOfFields", mappings.size());  
            model.addObject("msg3", "Mapping success");
        }
       
        model.setViewName("header");

        return model;
        
    }




}
