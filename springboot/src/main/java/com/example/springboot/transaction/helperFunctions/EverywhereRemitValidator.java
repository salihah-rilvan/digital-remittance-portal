package com.example.springboot.transaction.helperFunctions;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Arrays;

public class EverywhereRemitValidator {
    public static HashMap<String, Object>validateEverywhereRemit(Map<String, Object> map) {
        
        // Stores code (1 if no errors, 0 if there are errors) and list of errors in validation
        HashMap<String, Object> validateMsg = new HashMap<String, Object>();
        List<String> validationList = new ArrayList<String>();
        String code = "1";
        
        
        // Checks if transfer amount is a non-zero postive number
        try {
            if ( !(((String)map.get("units")).matches("^[+]?\\d+([.]\\d+)?$")) ) {
                validationList.add("Amount must be in a positive number");
                code = "0";
                validateMsg.put("code", code);
                validateMsg.put("error", validationList);
                return validateMsg;
            } 
        } catch (Exception e) {
        }


        try {
            if (((String)map.get("sender_legal_name_first")).length() > 50 ) {
                validationList.add("First name must below 50 characters");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            if ( !((String)map.get("sender_legal_name_first")).matches("^[A-Za-z0-9]*$") ) {
                validationList.add("First name must be letters and/or digits");
                code = "0";
            } 
        } catch (Exception e) {
        }


        //sender_legal_name_last: max 50, any letter (regardless of case) or digit will match
        try {
            if (((String)map.get("sender_legal_name_last")).length() > 50) {
                validationList.add("Last name must be below 50 characters.");
                code = "0";
            } 
            if ( !((String)map.get("sender_legal_name_last")).matches("^[A-Za-z0-9]*$")) {
                validationList.add("Last name must be letters and/or digits.");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            if ( !((String)map.get("sender_date_of_birth")).matches("\\d{4}-\\d{1,2}-\\d{1,2}")) {
                validationList.add("Date of Birth should be in YYYY-MM-DD format");
                code = "0";
            } 
        } catch (Exception e) {
        }

        
        try {
            if ( ((String)map.get("sender_nationality")).length() != 3) {
                validationList.add("Sender Nationality must between 3 characters");
                code = "0";
            } 
        } catch (Exception e) {
        }
        

        try {
            List<String> senderNationalityList = Arrays.asList("SGP", "MYS", "CHN", "IND", "AFG", "ALB", "DZA", "USA", "ASM", "AND", "AGO", "AIA", "ATA", "ATG", "ARG", "ARM", "ABW", "AUS", "AUT", "AZE", "IOT", "BHS", "BHR", "BGD", "BRB", "BLM", "LSO", "BLR", "BEL", "BLZ", "BEN", "BMU", "BTN", "GNB", "BOL", "BES", "BIH", "BVT", "BRA", "VGB", "GBR", "BRN", "BGR", "BFA", "MMR", "BDI", "CPV", "KHM", "CMR", "CAN", "CYM", "CAF", "TCD", "JEY", "GGY", "CHL", "TWN", "CXR", "CCK", "COL", "COM", "COG", "COD", "COK", "CRI", "HRV", "CUB", "CUW", "CYP", "CZE", "DNK", "DJI", "DMA", "DOM", "NLD", "ECU", "EGY", "ARE", "GNQ", "ERI", "EST", "ETH", "FLK", "FRO", "FJI", "FIN", "FRA", "GUF", "PYF", "ATF", "GAB", "GMB", "GEO", "DEU", "GHA", "GIB", "GRC", "GRL", "GRD", "GLP", "GUM", "GTM", "GIN", "GUY", "HTI", "HMD", "HND", "HKG", "HUN", "KIR", "ISL", "IDN", "IRN", "IRQ", "IRL", "ISR", "ITA", "CIV", "JAM", "JPN", "JOR", "KAZ", "KEN", "KNA", "KWT", "KGZ", "LAO", "LVA", "LBN", "LBR", "LBY", "LIE", "LTU", "LUX", "MAC", "MKD", "MYT", "MDG", "MWI", "MDV", "MLI", "MLT", "IMN", "MHL", "MTQ", "MRT", "MUS", "MEX", "FSM", "MDA", "MNG", "MNE", "MSR", "MCO", "MAR", "BWA", "MOZ", "NAM", "NRU", "NPL", "NCL", "NZL", "VUT", "NIC", "NGA", "NER", "NIU", "NFK", "PRK", "MNP", "NOR", "OMN", "PAK", "PLW", "PSE", "PAN", "PNG", "PRY", "PER", "PHL", "PCN", "POL", "PRT", "PRI", "QAT", "ROU", "RUS", "RWA", "REU", "ESH", "SHN", "LCA", "VCT", "MAF", "SPM", "SLV", "SMR", "WSM", "SAU", "SEN", "SRB", "SYC", "SLE", "SXM", "SVK", "SVN", "SLB", "SOM", "ZAF", "SGS", "KOR", "SSD", "ESP", "LKA", "SDN", "SUR", "SJM", "SWZ", "SWE", "CHE", "SYR", "STP", "TJK", "TZA", "THA", "TLS", "TGO", "TKL", "TON", "TTO", "TUN", "TUR", "TKM", "TCA", "TUV", "VIR", "UGA", "UKR", "URY", "UZB", "VAT", "VEN", "VNM", "WLF", "YEM", "ZMB", "ZWE", "ALA");
            boolean containSenderNationalityList = false;
            for (String item: senderNationalityList) {
                if (item.equals(map.get("sender_nationality"))) {
                    containSenderNationalityList = true;
                }
            }
            if (!containSenderNationalityList) {
                validationList.add("Country of address must be either \"SGP\", \"MYS\", \"CHN\", \"IND\", \"AFG\", \"ALB\", \"DZA\", \"USA\", \"ASM\", \"AND\", \"AGO\", \"AIA\", \"ATA\", \"ATG\", \"ARG\", \"ARM\", \"ABW\", \"AUS\", \"AUT\", \"AZE\", \"IOT\", \"BHS\", \"BHR\", \"BGD\", \"BRB\", \"BLM\", \"LSO\", \"BLR\", \"BEL\", \"BLZ\", \"BEN\", \"BMU\", \"BTN\", \"GNB\", \"BOL\", \"BES\", \"BIH\", \"BVT\", \"BRA\", \"VGB\", \"GBR\", \"BRN\", \"BGR\", \"BFA\", \"MMR\", \"BDI\", \"CPV\", \"KHM\", \"CMR\", \"CAN\", \"CYM\", \"CAF\", \"TCD\", \"JEY\", \"GGY\", \"CHL\", \"TWN\", \"CXR\", \"CCK\", \"COL\", \"COM\", \"COG\", \"COD\", \"COK\", \"CRI\", \"HRV\", \"CUB\", \"CUW\", \"CYP\", \"CZE\", \"DNK\", \"DJI\", \"DMA\", \"DOM\", \"NLD\", \"ECU\", \"EGY\", \"ARE\", \"GNQ\", \"ERI\", \"EST\", \"ETH\", \"FLK\", \"FRO\", \"FJI\", \"FIN\", \"FRA\", \"GUF\", \"PYF\", \"ATF\", \"GAB\", \"GMB\", \"GEO\", \"DEU\", \"GHA\", \"GIB\", \"GRC\", \"GRL\", \"GRD\", \"GLP\", \"GUM\", \"GTM\", \"GIN\", \"GUY\", \"HTI\", \"HMD\", \"HND\", \"HKG\", \"HUN\", \"KIR\", \"ISL\", \"IDN\", \"IRN\", \"IRQ\", \"IRL\", \"ISR\", \"ITA\", \"CIV\", \"JAM\", \"JPN\", \"JOR\", \"KAZ\", \"KEN\", \"KNA\", \"KWT\", \"KGZ\", \"LAO\", \"LVA\", \"LBN\", \"LBR\", \"LBY\", \"LIE\", \"LTU\", \"LUX\", \"MAC\", \"MKD\", \"MYT\", \"MDG\", \"MWI\", \"MDV\", \"MLI\", \"MLT\", \"IMN\", \"MHL\", \"MTQ\", \"MRT\", \"MUS\", \"MEX\", \"FSM\", \"MDA\", \"MNG\", \"MNE\", \"MSR\", \"MCO\", \"MAR\", \"BWA\", \"MOZ\", \"NAM\", \"NRU\", \"NPL\", \"NCL\", \"NZL\", \"VUT\", \"NIC\", \"NGA\", \"NER\", \"NIU\", \"NFK\", \"PRK\", \"MNP\", \"NOR\", \"OMN\", \"PAK\", \"PLW\", \"PSE\", \"PAN\", \"PNG\", \"PRY\", \"PER\", \"PHL\", \"PCN\", \"POL\", \"PRT\", \"PRI\", \"QAT\", \"ROU\", \"RUS\", \"RWA\", \"REU\", \"ESH\", \"SHN\", \"LCA\", \"VCT\", \"MAF\", \"SPM\", \"SLV\", \"SMR\", \"WSM\", \"SAU\", \"SEN\", \"SRB\", \"SYC\", \"SLE\", \"SXM\", \"SVK\", \"SVN\", \"SLB\", \"SOM\", \"ZAF\", \"SGS\", \"KOR\", \"SSD\", \"ESP\", \"LKA\", \"SDN\", \"SUR\", \"SJM\", \"SWZ\", \"SWE\", \"CHE\", \"SYR\", \"STP\", \"TJK\", \"TZA\", \"THA\", \"TLS\", \"TGO\", \"TKL\", \"TON\", \"TTO\", \"TUN\", \"TUR\", \"TKM\", \"TCA\", \"TUV\", \"VIR\", \"UGA\", \"UKR\", \"URY\", \"UZB\", \"VAT\", \"VEN\", \"VNM\", \"WLF\", \"YEM\", \"ZMB\", \"ZWE\" or \"ALA\" ");
                code = "0";
            } 
        } catch (Exception e) {
        }

        // Checks if first name is alphanumeric
        try {
            if (!((String)map.get("recipient_legal_name_first")).matches("^[A-Za-z0-9]*$")) {
                validationList.add("Recipient's first name must be letters and/or digits");
                code = "0";
            } 
            if ( ((String)map.get("recipient_legal_name_first")).length() > 50 ) {
                validationList.add("Recipient's first name must be less than 50 characters.");
                code = "0";
            } 
        } catch (Exception e) {
        }

        // Checks if first name is alphanumeric
        try {
            if ( !((String)map.get("recipient_legal_name_last")).matches("^[A-Za-z0-9]*$")) {
                validationList.add("Recipient's last name must be letters and/or digits.");
                code = "0";
            } 
            if ( ((String)map.get("recipient_legal_name_last")).length() > 50 ) {
                validationList.add("Recipient's last name must be less than 50 characters");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            if ( ((String)map.get("recipient_mobile_number")).equals("")) {
                validationList.add("Recipient's mobile number is required");
                code = "0";
            } 

        } catch (Exception e) {
        }

        try {
            if ( ((String)map.get("recipient_account_number")).length() > 128) {
                validationList.add("Recipient's EWallet ID must be below 128 characters");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            if ( !(((String)map.get("recipient_account_number")).matches("^[0-9]+$")) ) {
                validationList.add("Recipient's EWallet ID must be a digit");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            List<String> sourceOfFundsList = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08");
            boolean containSourceOfFundsList = false;
            for (String item : sourceOfFundsList) {
                if (item.equals(map.get("source_of_funds"))) {
                    containSourceOfFundsList = true;
                }
            }
            if (!containSourceOfFundsList) {
                validationList.add("Source of funds must be either \"01\" (Bank Deposit), \"02\" (Grant from family or friends), \"03\" (Redemption of investment products), \"04\" (Allowance for family maintenance), \"05\" (Loan), \"06\" (Salary), \"07\" (Real Estate) or \"08\" (Revenue)");
                code = "0";
        }   
        } catch (Exception e) {
        }
     
        try {
            List<String> remittancePurposeList = Arrays.asList("001-01", "001-02", "002-02", "003-01", "004-01", "005-01", "006-01", "007-01", "008-01", "008-02", "008-03");
            boolean containRemittancePurposeList = false;
            for (String item : remittancePurposeList) {
                if (item.equals(map.get("remittance_purpose"))) {
                    containRemittancePurposeList = true;
                }
            }
            if (!containRemittancePurposeList) {
                validationList.add("Remittance Purpose must be either \"001-01\" (Family/living expenses), \"001-02\" (Charity donation), \"002-02\" (Payment for services), \"003-01\" (Travel expenses), \"004-01\" (Personal asset allocation), \"005-01\" (Payment for goods), \"006-01\" (Capital transfer), \"006-02\" (Investment), \"007-01\" (Employee payroll), \"008-01\" (Goods trade), \"008-02\" (Services trade) or \"008-03\" (Return of export trade)");
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
