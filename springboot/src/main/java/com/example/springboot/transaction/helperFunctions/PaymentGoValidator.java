package com.example.springboot.transaction.helperFunctions;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Arrays;

public class PaymentGoValidator {


    // check for chinese characters
    public static int containsHanScript(String s) {
        for (int i = 0; i < s.length(); ) {
            int codepoint = s.codePointAt(i);
            i += Character.charCount(codepoint);
            if (Character.UnicodeScript.of(codepoint) == Character.UnicodeScript.HAN) {
                return 1;
            }
        }
        return -1;
    }

    public static HashMap<String, Object> validatePaymentGo(Map<String, Object> map) {
        
        // Stores code (1 if no errors, 0 if there are errors) and list of errors in validation
        HashMap<String, Object> validateMsg = new HashMap<String, Object>();
        List<String> validationList = new ArrayList<String>();
        String code = "1";

        // Checks if transfer amount is a non-zero postive number
        try {
            if (((String)map.get("merTransAmount")).equals("") | !((String)map.get("merTransAmount")).matches("^[+]?\\d+([.]\\d+)?$")) {
                validationList.add("Destination amount is required and it has to be a positive number.");
                code = "0";
                validateMsg.put("code", code);
                validateMsg.put("error", validationList);
                return validateMsg;
            } 
        } catch (Exception e) {
        }

        try {
            if (((String)map.get("remitGivenName")).equals("")) {
                validationList.add("Payer first name is required.");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            if (((String)map.get("remitSurname")).equals("")) {
                validationList.add("Payer last name is required.");
                code = "0";
            } 
        } catch (Exception e) {
        }



        try {
            List<String> nationality = Arrays.asList("SGP", "MYS", "CHN", "IND", "AFG", "ALB", "DZA", "USA", "ASM", "AND", "AGO", "AIA", "ATA", "ATG", "ARG", "ARM", "ABW", "AUS", "AUT", "AZE", "IOT", "BHS", "BHR", "BGD", "BRB", "BLM", "LSO", "BLR", "BEL", "BLZ", "BEN", "BMU", "BTN", 
            "GNB", "BOL", "BES", "BIH", "BVT", "BRA", "VGB", "GBR", "BRN", "BGR", "BFA", "MMR", "BDI", "CPV", "KHM", "CMR", "CAN", "CYM", "CAF", "TCD", "JEY", "GGY", "CHL", "TWN", "CXR", "CCK", "COL", "COM", "COG", "COD", "COK", "CRI", "HRV", "CUB", "CUW", "CYP", "CZE", "DNK", "DJI", "DMA", "DOM", "NLD", "ECU", "EGY", "ARE", "GNQ", "ERI", "EST", "ETH", "FLK", "FRO", "FJI", "FIN", "FRA", "GUF", "PYF", "ATF", "GAB", "GMB", "GEO", "DEU", "GHA", "GIB", "GRC", "GRL", "GRD", "GLP", "GUM", "GTM", "GIN", "GUY", "HTI", "HMD", "HND", "HKG", "HUN", "KIR", "ISL", "IDN", "IRN", "IRQ", "IRL", "ISR", "ITA", "CIV", "JAM", "JPN", "JOR", "KAZ", "KEN", "KNA", "KWT", "KGZ", "LAO", "LVA", "LBN", "LBR", "LBY", "LIE", "LTU", "LUX", "MAC", "MKD", "MYT", "MDG", "MWI", "MDV", "MLI", "MLT", "IMN", "MHL", "MTQ", "MRT", "MUS", "MEX", "FSM", 
            "MDA", "MNG", "MNE", "MSR", "MCO", "MAR", "BWA", "MOZ", "NAM", "NRU", "NPL", "NCL", "NZL", "VUT", "NIC", "NGA", "NER", "NIU", "NFK", "PRK", "MNP", "NOR", "OMN", "PAK", "PLW", "PSE", "PAN", "PNG", "PRY", "PER", "PHL", "PCN", "POL", "PRT", "PRI", "QAT", "ROU", "RUS", "RWA", "REU", "ESH", "SHN", "LCA", "VCT", "MAF", "SPM", "SLV", "SMR", "WSM", "SAU", "SEN", "SRB", "SYC", "SLE", "SXM", "SVK", "SVN", "SLB", "SOM", "ZAF", "SGS", "KOR", "SSD", "ESP", "LKA", "SDN", "SUR", "SJM", "SWZ", "SWE", "CHE", "SYR", "STP", "TJK", "TZA", "THA", "TLS", "TGO", "TKL", "TON", "TTO", "TUN", "TUR", "TKM", "TCA", "TUV", "VIR", "UGA", "UKR", "URY", "UZB", "VAT", "VEN", "VNM", "WLF", "YEM", "ZMB", "ZWE", "ALA");
            boolean containNationality = false;
            for (String item: nationality) {
                if (item.equals(map.get("nationality"))) {
                    containNationality = true;
                }
            }

            if (!containNationality) {
                validationList.add("Nationality is required and please select the corresponding 3 letter code.");
                code = "0";
            } 
        } catch (Exception e) {
        }
        
        try {
            List<String> remitCaTypeList = Arrays.asList("1", "2");
            boolean containRemitCaTypeList = false;
            for (String item : remitCaTypeList) {
                if (item.equals(map.get("remitCaType"))) {
                    containRemitCaTypeList = true;
                }
            }
    
            if (!containRemitCaTypeList) {
                validationList.add("Payer identification type is required and it must be either \"1\" for National ID or \"2\" for Passport.");
                code = "0";
            } 
        } catch (Exception e) {
        }


        try {
            if (((String)map.get("remitCountryCode")).equals("")) {
                validationList.add("Payer country is required.");
                code = "0";
            } 
        } catch (Exception e) {
        }


        try {
            if (((String)map.get("remitCaNo")).equals("")) {
                validationList.add("Payer identification number is required.");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            if (((String)map.get("remitAddress")).equals("")) {
                validationList.add("Payer Address is required.");
                code = "0";
            } 
        } catch (Exception e) {
        }
 
        try {
            if (((String)map.get("payeeGivenName")).equals("") | PaymentGoValidator.containsHanScript((String)map.get("payeeGivenName")) == -1){
                validationList.add("Payee first name is required and it has to be in Chinese.");
                code = "0";
            }
        } catch (Exception e) {
        }

        try {
            if (((String)map.get("payeeSurname")).equals("") | PaymentGoValidator.containsHanScript((String)map.get("payeeSurname")) == -1){
                validationList.add("Payee last name is required and it has to be in Chinese.");
                code = "0";
            }
        } catch (Exception e) {
        }

        try {
            if (((String)map.get("payeePhone")).equals("") | !((String)map.get("payeePhone")).matches("[0-9]+")) {
                validationList.add("Payee phone number is required and it has to be numerical.");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            if (((String)map.get("payeeAccountNo")).equals("")) {
                validationList.add("Payee account number is required.");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            List<String> senderSourceOfFundList = Arrays.asList("02", "07", "06", "04", "99", "01");
            boolean containSenderSourceOfFundList = false;
            for (String item: senderSourceOfFundList) {
                if (item.equals(map.get("sourceOfFunds"))) {
                    containSenderSourceOfFundList = true;
                }
            }
            if (!containSenderSourceOfFundList) {
                validationList.add("Source Of Funds must be either \"02\" (Business and investment), \"07\" (Cryptocurrency or other Digital Payment Tokens), \"06\" (Donation), \"04\" (Friends and family), \"99\" (Other) or \"01\" (Salary to include any work related compensation and pensions)");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            if (((String)map.get("payeeCaNo")).equals("")) {
                validationList.add("Payee identification number is required.");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            List<String> payeeCaTypeList = Arrays.asList("1", "2");
            boolean containPayeeCaTypeList = false;
            for (String item : payeeCaTypeList) {
                if (item.equals(map.get("payeeCaType"))) {
                    containPayeeCaTypeList = true;
                }
            }
    
            if (!containPayeeCaTypeList) {
                validationList.add("Payee identification type is required and it must be either \"1\" for National ID or \"2\" for Passport.");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            if ( !((String)map.get("payeeBirthDate")).matches("([0-9]{2})/([0-9]{2})/([0-9]{4})") && !((String)map.get("payeeBirthDate")).matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {
                validationList.add("Payee birthdate should be in DD-MM-YYY or DD/MM/YYYY format");
                code = "0";
            }
        } catch (Exception e) {
        }

        try {
            if (((String)map.get("payeeBankName")).equals("")) {
                validationList.add("Payee bank name is required.");
                code = "0";
            } 
        } catch (Exception e) {
        }


        try {
            if (((String)map.get("payeeBranchName")).equals("")) {
                validationList.add("Payee bank's branch name is required.");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            if (((String)map.get("remitAccountNo")).equals("")) {
                validationList.add("Payer account number is required.");
                code = "0";
            } 
        } catch (Exception e) {
        }

        // Return code (1 if no errors, 0 if there are errors) and list of errors in validation
        validateMsg.put("code", code);
        validateMsg.put("error", validationList);
        return validateMsg;
    }
}
