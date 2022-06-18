package com.example.springboot.receiver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ReceiverController {
    private final ReceiverService receiverService;

    @Autowired
    public ReceiverController(ReceiverService receiverService) {
        this.receiverService = receiverService;
    }


    @DeleteMapping(path = "{receiverId}")
    public void deleteReceiver(@PathVariable("receiverId") Long receiverId) {
        receiverService.deleteReceiver(receiverId);
    }

}