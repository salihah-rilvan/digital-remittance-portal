package com.example.springboot.ssot;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.springboot.ssotReceiverMap.SsotReceiverMapService;
import com.example.springboot.ssotSenderMap.SsotSenderMapService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class SsotController {
    
    @Autowired
    SsotService ssotService;

    @Autowired
    SsotSenderMapService ssotSenderMapService;

    @Autowired
    SsotReceiverMapService ssotReceiverMapService;


    // General function to show all SSOTs
    @RequestMapping(value = { "/admin" }, method = RequestMethod.GET)
    public ModelAndView admin() {
        ModelAndView model = new ModelAndView();
    
        // List all SSOTS
        List<Ssot> ssotList = ssotService.findAll();
        
        model.addObject("name", "Admin");
        model.addObject("ssotList", ssotList);
        model.setViewName("admin");
        return model;
    }

    // Create new SSOT
    @RequestMapping(value = { "/admin" }, method = RequestMethod.POST)
    public ModelAndView createNewSsot(@RequestParam("ssotName") String ssotName) {
        ModelAndView model = new ModelAndView();

        String msg = ssotService.createSsot(ssotName);
        
        // List all SSOTS
        List<Ssot> ssotList = ssotService.findAll();

        model.addObject("name", "Admin");
        model.addObject("msg", msg);
        model.addObject("ssotList", ssotList);
        model.setViewName("admin");
        return model;
    }


    @RequestMapping(value = { "/adminSettings" }, method = RequestMethod.GET)
    public ModelAndView adminSettings() {
        ModelAndView model = new ModelAndView();
        
        model.setViewName("adminSettings");
        return model;
    }

    // Delete Ssots
    @RequestMapping(value = { "/adminDeleteSsot" }, method = RequestMethod.POST)
    public ModelAndView deleteSsot(@RequestParam("deleteSsot") List<Long> deleteList) {
        if (deleteList.size() == 0 || deleteList == null) {
            return new ModelAndView("redirect:/admin");
        }

        ModelAndView model = new ModelAndView();
        model.addObject("msg3", "Object not deleted");
        
        for (long current : deleteList) {
            ssotService.deleteSsot(current);
            ssotSenderMapService.deleteSsotBySsotId(current);
            ssotReceiverMapService.deleteSsotBySsotId(current);
        }
        model.addObject("msg3", "Deleted successfully");
        
    
        List<Ssot> ssotList = ssotService.findAll();

        model.addObject("name", "Admin");
        model.addObject("ssotList", ssotList);
        model.setViewName("admin");
        return model;
    }

    // Edit ssotnames
    @RequestMapping(value = { "/adminEditSsot" }, method = RequestMethod.POST)
    public ModelAndView editSsot(@RequestParam Map<String,String> editList) {
        if (editList.size() == 0 || editList == null) {
            return new ModelAndView("redirect:/admin");
        }

        ssotService.changeSsotNames(editList);
        ModelAndView model = new ModelAndView();
        List<Ssot> ssotList = ssotService.findAll();
        model.addObject("msg2", "Changes saved");
        model.addObject("name", "Admin");
        model.addObject("ssotList", ssotList);
        model.setViewName("admin");
        return model;
    }
}
