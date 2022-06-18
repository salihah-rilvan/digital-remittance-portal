package com.example.springboot.transaction.helperFunctions;

import java.util.HashMap;
import java.util.Map;

public class DataValidationChecker {
    public static HashMap<String, Object> validate(Map<String, Object> map) {
        
        // Store validation errors into HashMap
        HashMap<String, Object> result = new HashMap<String, Object>();

        // Checks which validator to use for transaction
        if (map.get("api_name").equals("financenow")) {
            result = FinanceNowValidator.validateFinanceNow((Map<String, Object>)map.get("payload"));
        } else if (map.get("api_name").equals("everywhereremit")) {
            result = EverywhereRemitValidator.validateEverywhereRemit((Map<String, Object>)map.get("payload"));
        } else if (map.get("api_name").equals("paymentgo")) {
            result = PaymentGoValidator.validatePaymentGo((Map<String, Object>)map.get("payload"));
        }
        
        return result;
    }
}
