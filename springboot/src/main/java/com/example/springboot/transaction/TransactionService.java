package com.example.springboot.transaction;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.springboot.excelHelper.ExcelHelper;
import com.example.springboot.ssot.Ssot;
import com.example.springboot.ssotReceiverMap.SsotReceiverMap;
import com.example.springboot.ssotReceiverMap.SsotReceiverMapRepository;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.vladmihalcea.hibernate.type.json.internal.JacksonUtil;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository repository;


    @Autowired
    SsotReceiverMapRepository ssotReceiverMapRepository;

    public void save(List<Transaction> transactions) {
        repository.saveAll(transactions);
    }

    // Parse the excel file using Excel Helper class
    public List<Transaction> parse(MultipartFile file, long senderId, Map<Long, String> ssots, HashMap<Long, String> fields) {
        try {
            List<Transaction> transactions = ExcelHelper.excelToTransaction(file.getInputStream(), senderId, ssots, fields);
            return transactions;
        } catch (IOException e) {
            throw new RuntimeException("Fail to store Excel data: " + e.getMessage());
        }
    }

    public Map<String, Object> changeSsotToReceiverHeaders(Transaction transaction, String access_token, List<Ssot> ssotList){

        // prepare access token
        JsonNode jsonToken= JacksonUtil.toJsonNode(access_token);
        String token = jsonToken.get("access_token").asText();

        // prepare payload
        JsonNode current = transaction.getJsonNodeArgsInfo();
        String api_name = "";
        int api_id = 0;
        
        // check amount for value
        double amount= current.get("amount").asDouble();
        
        // if amount <3000 SGD : FinanceNow (id = 2)
        if (amount <= 3000) {
            api_name = "financenow";
            api_id = 2;
        
        // 3001 SGD - 6000 SGD : EverywhereRemit (id = 1)
        } else if (amount > 3000 && amount <= 6000) {
            api_name = "everywhereremit";
            api_id = 1;
        
        // 6001 SGD - 10000 SGD : PaymentGo (id = 3)
        } else {
            api_name = "paymentgo";
            api_id = 3;
        }

        // change receiver_id to api_id
        transaction.setReceiverId(api_id);

        // get SsotReceiverMap 
        Collection<SsotReceiverMap> ssotReceiverMapList = ssotReceiverMapRepository.findReceiverById(api_id);

        // create container for ssotId to value
        HashMap<String, String> map = new HashMap<String, String>();
        
        // if SsotReceiverMap.ssotId == ssot.ssotId, if ssot.name == field.name, store SsotReceiverMap.name : field.value
        for (SsotReceiverMap ssotReceiverMap : ssotReceiverMapList) {
            for (Ssot ssot: ssotList) {
                if (ssotReceiverMap.getSsotId() == ssot.getSsotId()) {
                    Iterator<String> iterator = current.fieldNames();
                    while (iterator.hasNext()) {
                        String fieldName = iterator.next();
                        if(fieldName.equals(ssot.getSsotName())){
                            map.put(ssotReceiverMap.getReceiverFieldName(), current.get(fieldName).asText());
                        }
                            
                    }
                }
            }
        }

        // adding hidden fields with default values for EverywhereRemit
        if (api_name.equals("everywhereremit")) {
            map.put("recipient_type", "bank_account");
            map.put("recipient_country", "CHN");
            map.put("recipient_currency", "CNY");
            map.put("source_type", "partner");
            map.put("segment", "individual");
            map.put("sender_country", "SGP");
            map.put("sender_currency", "SGD");
            map.put("sender_address_country", map.get("sender_id_country"));
        }

        // adding hidden fields with default values for PaymentGo
        if (api_name.equals("paymentgo")) {
            map.put("settleCurrency", "sourceCurrency");
            map.put("remitPurpose", "131");
            map.put("transCurrency", "CNY");
        }

        // format hashmap to send to receiver
        Map<String, Object> post = new HashMap<String, Object>();
        
        post.put("payload", map);
        post.put("api_name", api_name);
        post.put("access_token", token);
        return post;
    }

    public ArrayList<Transaction> getTransactionBySender(long id){
        return repository.getTransactionBySender(id);
    }

    public ArrayList<Transaction> getTransactionBySenderAndReceiver(long sender_id, long receiver_id){
        ArrayList<Transaction> allTransations = repository.getTransactionBySender(sender_id);
        ArrayList<Transaction> transactionsByReceiver = new ArrayList<>();
        for (Transaction t: allTransations){
            if  (t.getReceiverId() == receiver_id){
                transactionsByReceiver.add(t);
            }
        }
        return transactionsByReceiver;
    }

    public ArrayList<Transaction> getTransactionByAmount(long sender_id, double amount ){
        ArrayList<Transaction> allTransations = repository.getTransactionBySender(sender_id);
        ArrayList<Transaction> transactionsByAmount = new ArrayList<>();
        for (Transaction t: allTransations){
            JsonNode current = t.getJsonNodeArgsInfo();
            double tAmount= current.get("amount").asDouble();
            if  (tAmount == amount){
                transactionsByAmount.add(t);
            }
        }
        return transactionsByAmount;
    }

    public ArrayList<Transaction> getTransactionByStatus(long sender_id, String status){
        ArrayList<Transaction> allTransations = repository.getTransactionBySender(sender_id);
        ArrayList<Transaction> transactionsByStatus = new ArrayList<>();
        for (Transaction t: allTransations){
            if  (t.getStatus().equals(status)){
                transactionsByStatus.add(t);
            }
        }
        return transactionsByStatus;
    }


    public ArrayList<Transaction> getAllFailTransactions(){
        ArrayList<Transaction> allFailTransations = repository.getAllFailTransactions();
        return  allFailTransations;
    }

    public ArrayList<Transaction> getAllTransactions(){
        ArrayList<Transaction> allTransations = repository.getAllTransactions();
        return  allTransations;
    }

    public ArrayList<Transaction> getTransactionByDateTime(long sender_id, String dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        LocalDate dt = LocalDate.parse(dateTime, formatter);
        ArrayList<Transaction> allTransations = repository.getTransactionBySender(sender_id);
        ArrayList<Transaction> transactionsByDateTime = new ArrayList<>();
        for (Transaction t: allTransations){
            if  (t.getDateTime().isEqual(dt)){
                transactionsByDateTime.add(t);
            }
        }
        return transactionsByDateTime;
    }

    public void saveTransaction(Transaction transaction) {
        repository.save(transaction);
    }

    

}
