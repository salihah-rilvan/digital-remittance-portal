package com.example.springboot.transaction.helperFunctions;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Arrays;

public class FinanceNowValidator {
    public static HashMap<String, Object> validateFinanceNow(Map<String, Object> map) {
        
        // Stores code (1 if no errors, 0 if there are errors) and list of errors in validation
        HashMap<String, Object> validateMsg = new HashMap<String, Object>();
        List<String> validationList = new ArrayList<String>();
        String code = "1";
        
        // Checks if transfer amount is a non-zero postive number
        try {
            if (((String)map.get("TransferAmount")).equals("") || !((String)map.get("TransferAmount")).matches("^[+]?\\d+([.]\\d+)?$") || (((String)map.get("TransferAmount")).equals("0"))) {
                validationList.add("TransferAmount is required and it has to be a positive number.");
                code = "0";
                validateMsg.put("code", code);
                validateMsg.put("error", validationList);
                return validateMsg;
            } 
        } catch (Exception e) {
        }

        try {
            if (((String)map.get("BankAccountNumber")).length() > 20 || ((String)map.get("BankAccountNumber")).length() == 0) {
                validationList.add("BankAccountNumber must be between 1 to 20 characters");
                code = "0";
            } 
        } catch (Exception e) {
            
        }

        try {
            if (((String)map.get("PaymentMode")).length() > 50 || ((String)map.get("PaymentMode")).length() < 0) {
                validationList.add("PaymentMode must be between 0 to 50 characters");
                code = "0";
            } 
        } catch (Exception e) {
        }
        
        try {
            if (((String)map.get("PayoutCurrency")).length() != 3) {
                validationList.add("PayoutCurrency must be exactly 3 characters");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            if (((String)map.get("PurposeOfRemittance")).length() > 3 || ((String)map.get("PurposeOfRemittance")).length() == 0) {
                validationList.add("PurposeOfRemittance must be between 1 to 3 characters");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            List<String> purposeOfRemittanceValidation = Arrays.asList("16", "12", "05", "03", "02", "99", "06", "10");
            boolean containPurposeOfRemittanceValidation = false;
            for (String item : purposeOfRemittanceValidation) {
                if (item.equals(map.get("PurposeOfRemittance"))) {
                    containPurposeOfRemittanceValidation = true;
                }
            }
            if (!containPurposeOfRemittanceValidation) {
                validationList.add("PurposeOfRemittance must be either \"16\" (Business Expenses), \"12\" (Construction, purchase of land and mortgage repayments), \"05\" (Donation), \"03\" (Education), \"02\" (Family Expenses), \"99\" (Other), \"06\" (Payment for products and services) or\"10\" (Self)");
                code = "0";
            } 
        } catch (Exception e) {
        }


        try {
            if (((String)map.get("ReceiverAddress")).length() > 25 || ((String)map.get("ReceiverAddress")).length() == 0) {
                validationList.add("ReceiverAddress must be between 1 to 25 characters");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            if (((String)map.get("ReceiverCity")).length() > 25 || ((String)map.get("ReceiverCity")).length() == 0) {
                validationList.add("ReceiverCity must be between 1 to 25 characters");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            if (((String)map.get("ReceiverCountry")).length() > 3 || ((String)map.get("ReceiverCountry")).length() == 0) {
                validationList.add("ReceiverCountry must be between 1 to 3 characters");
                code = "0";
            } 
        } catch (Exception e) {
        }

        
        try {
            if (((String)map.get("ReceiverFirstName")).length() > 50 || ((String)map.get("ReceiverFirstName")).length() == 0) {
                validationList.add("ReceiverFirstName must be between 1 to 50 characters");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            if (((String)map.get("ReceiverIdNumber")).length() > 18 || ((String)map.get("ReceiverIdNumber")).length() == 0) {
                validationList.add("ReceiverIdNumber must be between 1 to 18 characters");
                code = "0";
            }
        } catch (Exception e) {
        }
 
        try {
            if (((String)map.get("ReceiverIdType")).length() != 2) {
                validationList.add("ReceiverIdType must be exactly 2 characters");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            List<String> receiverIdTypeList = Arrays.asList("05", "02", "99", "01");
            boolean containReceiverIdTypeList = false;
            for (String item : receiverIdTypeList) {
                if (item.equals(map.get("ReceiverIdType"))) {
                    containReceiverIdTypeList = true;
                }
            }
            if (!containReceiverIdTypeList) {
                validationList.add("ReceiverIdType must be either \"05\" (Company Registration), \"02\" (ID card (government issued)),\"99\" (Other) or \"01\"(Passport)");
                code = "0";
            }
        } catch (Exception e) {
        }
 
        try {
            if (((String)map.get("ReceiverLastName")).length() > 30 || ((String)map.get("ReceiverLastName")).length() == 0) {
                validationList.add("ReceiverLastName must be between 1 to 30 characters");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            if (((String)map.get("SenderAddress")).length() > 60) {
                validationList.add("SenderAddress must be max 60 characters");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            if (((String)map.get("SenderBeneficiaryRelationship")).length() > 10 || ((String)map.get("SenderBeneficiaryRelationship")).length() == 0) {
                validationList.add("SenderBeneficiaryRelationship must be between 1 to 10 characters");
                code = "0";
            }
        } catch (Exception e) {
        }
 
        try {
            List<String> SenderBeneficiaryRelationshipList = Arrays.asList("04","01", "02", "99", "03", "09");
            boolean containSenderBeneficiaryRelationship = false;
            for (String item: SenderBeneficiaryRelationshipList) {
                if (item.equals(map.get("SenderBeneficiaryRelationship"))) {
                    containSenderBeneficiaryRelationship = true;
                } 
            } 
            if (!containSenderBeneficiaryRelationship) {
                validationList.add("SenderBeneficiaryRelationship must be either \"04\" (EMPLOYEE), \"01\" (FAMILY), \"02\" (FRIENDS), \"99\" (OTHERS), \"03\" (SELF) or \"09\"(Seller/Service Provider)");
                code = "0";
            }
        } catch (Exception e) {
        }

        
        try {
            if (((String)map.get("SenderCity")).length() > 50) {
                validationList.add("SenderCity must max 50 characters");
                code = "0";
            }
        } catch (Exception e) {
        }
 
        try {
            if (((String)map.get("SenderCountry")).length() > 3) {
                validationList.add("SenderCountry must max 3 characters");
                code = "0";
            } 
        } catch (Exception e) {
        }
        
        try {
            if (((String)map.get("SenderDateOfBirth")).length() > 11 || ((String)map.get("SenderDateOfBirth")).length() == 0) {
                validationList.add("SenderDateOfBirth must between 1 to 11 characters");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            if (((String)map.get("SenderFirstName")).length() > 50 || ((String)map.get("SenderFirstName")).length() == 0) {
                validationList.add("SenderFirstName must between 1 to 50 characters");
                code = "0";
            } 
        } catch (Exception e) {
        }
        
        try {
            if (((String)map.get("SenderIdNumber")).length() > 13 || ((String)map.get("SenderIdNumber")).length() == 0) {
                validationList.add("SenderIdNumber must between 1 to 13 characters");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            if (((String)map.get("SenderIdType")).length() > 13 || ((String)map.get("SenderIdType")).length() == 0) {
                validationList.add("SenderIdType must exactly 2 characters");
                code = "0";
            } 
        } catch (Exception e) {
        }


        try {
            List<String> senderIdTypeList = Arrays.asList("05", "02", "99", "01");
            boolean containSenderIdTypeList = false;
            for (String item : senderIdTypeList) {
                if (item.equals(map.get("SenderIdType"))) {
                    containSenderIdTypeList = true;
                }
            }
            if (!containSenderIdTypeList) {
                validationList.add("SenderIdType must be either \"05\" (Company Registration), \"02\" (ID card), \"99\" (Other) or \"01\" (Passport)");
                code = "0";
            }
        } catch (Exception e) {
        }

        
  
        try {
            if (((String)map.get("SenderLastName")).length() > 50 || ((String)map.get("SenderLastName")).length() == 0) {
                validationList.add("SenderLastName must between 1 to 50 characters");
                code = "0";
            } 
        } catch (Exception e) {
        }
   
        try {
            if (((String)map.get("SenderNationality")).length() > 3 || ((String)map.get("SenderNationality")).length() == 0) {
                validationList.add("SenderNationality must between 1 to 3 characters");
                code = "0";
            }
        } catch (Exception e) {
        }
 
        try {
            List<String> senderNationalityList = Arrays.asList("AUS", "BGD", "CHN", "EUR", "GBR", "HKG", "IDN", "IND", "JPN", "KHM", "KOR", "LKA", "MMR", "MYS", "NPL", "PAK", "PHL", "THA");
            boolean containSenderNationalityList = false;
            for (String item: senderNationalityList) {
                if (item.equals(map.get("SenderNationality"))) {
                    containSenderNationalityList = true;
                }
            }
    
            if (!containSenderNationalityList) {
                validationList.add("SenderNationality must be either \"AUS\", \"BGD\", or \"CHN\", \"EUR\", or \"GBR\", \"HKG\" or \"IDN\" or \"IND\", \"JPN\", \"KHM\", \"KOR\", \"LKA\", \"MMR\", \"MYS\", \"NPL\", \"PAK\", \"PHL\" or \"THA\"");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            if (((String)map.get("SenderSourceOfFund")).length() > 10 || ((String)map.get("SenderSourceOfFund")).length() == 0) {
                validationList.add("SenderSourceOfFund must between 1 to 10 characters");
                code = "0";
            }
        } catch (Exception e) {
        }

        try {
            List<String> senderSourceOfFundList = Arrays.asList("02", "07", "06", "04", "99", "01");
            boolean containSenderSourceOfFundList = false;
            for (String item: senderSourceOfFundList) {
                if (item.equals(map.get("SenderSourceOfFund"))) {
                    containSenderSourceOfFundList = true;
                }
            }
            if (!containSenderSourceOfFundList) {
                validationList.add("SenderSourceOfFund must be either \"02\" (Business and investment), \"07\" (Cryptocurrency or other Digital Payment Tokens), \"06\" (Donation), \"04\" (Friends and family), \"99\" (Other) or \"01\" (Salary to include any work related compensation and pensions)");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            if (((String)map.get("SenderState")).length() > 20) {
                validationList.add("SenderState must max 20 characters");
                code = "0";
            } 
        } catch (Exception e) {
        }

        try {
            if (((String)map.get("ReceiverNationality")).length() > 3 || ((String)map.get("ReceiverNationality")).length() == 0) {
                validationList.add("ReceiverNationality must between 1 to 3 characters");
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
