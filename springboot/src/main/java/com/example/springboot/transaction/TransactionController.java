package com.example.springboot.transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.springboot.excelHelper.ExcelHelper;
import com.example.springboot.sender.SenderService;
import com.example.springboot.ssot.Ssot;
import com.example.springboot.ssot.SsotService;
import com.example.springboot.ssotSenderMap.SsotSenderMapService;
import com.fasterxml.jackson.databind.JsonNode;

import com.vladmihalcea.hibernate.type.json.internal.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;



@CrossOrigin("http://localhost:8081")
@Controller
// @RequestMapping("/api/csv")
public class TransactionController {
    @Autowired
    SenderService senderService;

    @Autowired
    RestService restService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    SsotService ssotService;

    @Autowired
    private SsotSenderMapService ssotSenderMapService;

    // Processing upload file with transactions
    @RequestMapping(value = { "/submitNew" }, method = RequestMethod.POST)
    public ModelAndView uploadFile(@RequestParam("file") MultipartFile file, HttpSession session) {
        long senderId = (Long) session.getAttribute("senderid");

        ModelAndView model = new ModelAndView();
        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                // get SSOTs list and convert to HashMap 
                List<Ssot> ssotObjects = ssotService.findAll();
                Map<Long, String> ssots = ssotObjects.stream().collect(Collectors.toMap(Ssot::getSsotId, Ssot::getSsotName));
                
                // Get field mapping of the sender
                HashMap<Long,String> fields = ssotSenderMapService.getSenderField(senderId);

                // Parse the excel file into a list of transactions using fields mapping and ssots
                // throw error when the input does not match with submitted fields
                List<Transaction> transactions = transactionService.parse(file, senderId, ssots, fields);

                // Save transaction to session
                session.setAttribute("transactions", transactions);

                // Send objects to front end
                model.addObject("msg2", "File upload success");
                model.addObject("transactions", transactions);
                model.addObject("transactions_rdy", true);
                model.addObject("status", true);
                model.setViewName("submitNew");
                
            } catch (Exception e) {
                // Send error message to front end
                model.addObject("msg3", e.getMessage());
                model.addObject("status", false);
                model.setViewName("submitNew");
            }
        
        } else {
            // Send error message to front end (when user submit a file other than Excel)
            model.addObject("msg3", "Please upload an Excel file");
            model.addObject("status", false);
            model.setViewName("submitNew");
        }
        return model;
    }

    // Action when user clicks Send Transaction
    @RequestMapping(value = "/sendTransaction", method = RequestMethod.POST)
    public ModelAndView sendTransaction(HttpSession session) {
        ModelAndView model = new ModelAndView("transaction"); 
        List<Transaction> transactions = (List<Transaction>) session.getAttribute("transactions");

        // list of messages
        List<String> messages = new ArrayList<String>();

        // HashMap to store validation errors for each transaction
        HashMap<Transaction, List<String>> transactionErrors = new HashMap<Transaction, List<String>>();

        // Get access token to send transaction
        String access_token = restService.getAccessToken();

        // Get all ssot
        List<Ssot> ssotList = ssotService.findAll();

        
        for (Transaction transaction : transactions) {
            
            // Change headers to receiver's headers
            Map<String, Object> map= transactionService.changeSsotToReceiverHeaders(transaction, access_token, ssotList);
            
            // List of errors when validating data
            List<String> validationErrors = new ArrayList<String>();

            // Send transaction, and get response
            String status = restService.sendTransaction(map);
            
            // Format response into JsonNode
            JsonNode statusMsg= JacksonUtil.toJsonNode(status);

            // Check response for errors 
            Iterator<String> statusFieldNames = statusMsg.fieldNames();
            String fieldsMissing = "Fields Missing. Transaction not processed.";
            boolean statusError = false;

            while(statusFieldNames.hasNext()) {
                if((statusFieldNames.next()).equals("error")) {

                    // Fields missing error
                    if (statusMsg.get("error").asText().equals(fieldsMissing)) {
                        validationErrors.add(fieldsMissing);
                        transaction.setStatus("error");
                        messages.add("error");
                    }

                    // Errors in data format
                    else if (statusMsg.get("code").asInt() == 0) {
                        JsonNode errorMessages = statusMsg.get("error");
                        transaction.setStatus("error");
                        messages.add("error");
        
                        // Put error messages into validation errors list
                        for (JsonNode errorMsg : errorMessages) {
                            validationErrors.add(errorMsg.asText());
                        }
                    } 
                    statusError = true;
                } 
            }

            // If no errors found, report success message
            if (!statusError) {
                String successMsg = statusMsg.get("message").asText();
                if (successMsg.contains("Successful")){
                    transaction.setStatus("success");
                }else if (successMsg.contains("Pending")){
                    transaction.setStatus("pending");
                }else{
                    transaction.setStatus("rejected");
                }
                messages.add(successMsg);
                validationErrors.add("No errors!");
            }

            // Add save list of transaction errors with transaction as the key
            transactionErrors.put(transaction, validationErrors);
        }

        // After processing transaction, save it into database
        transactionService.save(transactions);

        // Return response messages to user
        model.addObject("transactions",transactions);
        model.addObject("messages", messages);
        model.addObject("error", transactionErrors);
        return model;
    }

    //geting all past transactions for sender
    @RequestMapping(value = "/transaction", method = RequestMethod.GET)
    public ModelAndView getTransactionBySender (HttpSession session) {
        long senderId = (Long) session.getAttribute("senderid");

        ModelAndView model = new ModelAndView();
        ArrayList<Transaction> t = transactionService.getTransactionBySender(senderId);  
        model.addObject("t", t);
        model.setViewName("transaction");
        return model;
    }

    // Get transactions by status
    @RequestMapping(value = "/transaction/status/{status}", method = RequestMethod.GET)
    public ModelAndView getTransactionByStatus (@PathVariable String status, HttpSession session) {
        long senderId = (Long) session.getAttribute("senderid");
    
        ModelAndView model = new ModelAndView();
        ArrayList<Transaction> t = transactionService.getTransactionByStatus(senderId, status);  
        model.addObject("t", t);
        model.setViewName("transaction");
        return model;
    }

    // Get transactions by date
    @RequestMapping(value = "/transaction/date/{date}", method = RequestMethod.GET)
    public ModelAndView getTransactionByDateTime (@PathVariable String date, HttpSession session) {
        long senderId = (Long) session.getAttribute("senderid");

        ModelAndView model = new ModelAndView();
        ArrayList<Transaction> t = transactionService.getTransactionByDateTime(senderId, date);  
        model.addObject("t", t);
        model.setViewName("transaction");
        return model;
    }

    
    // Get transactions by receiver
    @RequestMapping(value = "/transaction/receiver/{receiver_id}", method = RequestMethod.GET)
    public ModelAndView getTransactionByDateTime (@PathVariable Long receiver_id, HttpSession session) {
        long senderId = (Long) session.getAttribute("senderid");

        ModelAndView model = new ModelAndView();
        ArrayList<Transaction> t = transactionService.getTransactionBySenderAndReceiver(senderId, receiver_id);  
        model.addObject("t", t);
        model.setViewName("transaction");
        return model;
    }

    
    // Get transactions by amount
    @RequestMapping(value = "/transaction/amount/{amount}", method = RequestMethod.GET)
    public ModelAndView getTransactionByDateTime (@PathVariable double amount, HttpSession session) {
        long senderId = (Long) session.getAttribute("senderid");

        ModelAndView model = new ModelAndView();
        ArrayList<Transaction> t = transactionService.getTransactionByAmount(senderId, amount); 
        model.addObject("t", t);
        model.setViewName("transaction");
        return model;
    }

    // Get all transactions for admin 
    @RequestMapping(value = { "/adminTransactions" }, method = RequestMethod.GET)
    public ModelAndView adminTransactions() {
        ModelAndView model = new ModelAndView();
        ArrayList<Transaction> fail = transactionService.getAllFailTransactions();
        ArrayList<Transaction> all = transactionService.getAllTransactions();
        model.addObject("failList", fail);
        model.addObject("allList", all);
        model.setViewName("adminTransactions");
        return model;
    }


}
