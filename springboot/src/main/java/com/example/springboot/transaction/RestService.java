package com.example.springboot.transaction;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.springboot.transaction.helperFunctions.DataValidationChecker;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class RestService {
    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getAccessToken() {
        String url = "https://prelive.paywho.com/api/smu_authenticate";

        // create headers
        HttpHeaders headers = new HttpHeaders();

        // set content type header
        headers.setContentType(MediaType.APPLICATION_JSON);

        //set 'accept' header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // create map for post parameters
        Map<String, String> map = new HashMap<String, String>();
        map.put("username","oops");
        map.put("password", "wediditagain");
        
        // build the requests
        HttpEntity<Map<String, String>> request = new HttpEntity<>(map, headers);

        //send Post request
        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class);
        
        // check response status code
        return response.getBody();
       
    }

    public String sendTransaction(Map<String, Object> map) {
        
        // check values if valid 
        HashMap<String, Object> result = DataValidationChecker.validate(map);

        // if there are validation errors
        if (((String)result.get("code")).equals("0")) {
            
            // convert validation result to json string
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String json_errors = objectMapper.writeValueAsString(result);
                
                // return errors messages back to controller
                return json_errors;
                
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        // Send transaction link
        String url = "https://prelive.paywho.com/api/smu_send_transaction";

        // create headers
        HttpHeaders headers = new HttpHeaders();
        
        // set content type header
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // set 'accept' header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // build the requests
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
        
        //send Post request to sandbox
        ResponseEntity<String> response = this.restTemplate.postForEntity(url, request, String.class);
        
        // check response status code
        return response.getBody();
    }

}
