package com.example.springboot.ssotReceiverMap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.*;

@Configuration
public class SsotReceiverMapConfig {
    @Bean
    CommandLineRunner ssotReceiverMapCommandLineRunner(
        SsotReceiverMapRepository repository) {
            return args ->{
                List<SsotReceiverMap> ssotReceiverMap = new ArrayList<SsotReceiverMap>();
                Collections.addAll(ssotReceiverMap, 
                    new SsotReceiverMap(1,1,1, "sender_legal_name_first"), 
                    new SsotReceiverMap(2,2,1, "sender_legal_name_last"), 
                    new SsotReceiverMap(3,3,1, "sender_date_of_birth"), 
                    new SsotReceiverMap(4,4,1, "sender_nationality"),
                    new SsotReceiverMap(5,5,1,"sender_id_type"), 
                    new SsotReceiverMap(6,6,1,"sender_country"),
                    new SsotReceiverMap(7,7,1,"sender_id_number"), 
                    new SsotReceiverMap(8,8,1,"sender_currency"),
                    new SsotReceiverMap(9,9,1,"sender_address_line"),
                    new SsotReceiverMap(10,10,1,"sender_address_city"),
                    new SsotReceiverMap(11,11,1,"sender_country_code"),
                    new SsotReceiverMap(12,12,1,"recipient_country"),
                    new SsotReceiverMap(13,13,1,"recipient_legal_name_first"),
                    new SsotReceiverMap(14,14,1,"recipient_legal_name_last"),
                    new SsotReceiverMap(15,15,1,"recipient_mobile_number"),
                    new SsotReceiverMap(16,16,1,"recipient_account_number"),
                    new SsotReceiverMap(17,17,1,"recipient_currency"),
                    new SsotReceiverMap(18,18,1,"units"),
                    new SsotReceiverMap(19,19,1,"source_of_funds"),
                    new SsotReceiverMap(20,20,1,"remittance_purpose"),
                    new SsotReceiverMap(21,32,1,"sender_id_country"),
                    new SsotReceiverMap(22,1,2,"SenderFirstName"), 
                    new SsotReceiverMap(23,2,2,"SenderLastName"), 
                    new SsotReceiverMap(24,3,2,"SenderDateOfBirth"), 
                    new SsotReceiverMap(25,4,2,"SenderNationality"),
                    new SsotReceiverMap(26,5,2,"SenderIdType"), 
                    new SsotReceiverMap(27,6,2,"SenderCountry"),
                    new SsotReceiverMap(28,7,2,"SenderIdNumber"), 
                    new SsotReceiverMap(29,8,2,"PayoutCurrency"),
                    new SsotReceiverMap(30,9,2,"SenderAddress"),
                    new SsotReceiverMap(31,10,2,"SenderCity"),
                    new SsotReceiverMap(32,18,2,"TransferAmount"),
                    new SsotReceiverMap(33,12,2,"ReceiverCountry"),
                    new SsotReceiverMap(34,13,2,"ReceiverFirstName"),
                    new SsotReceiverMap(35,14,2,"ReceiverLastName"),
                    new SsotReceiverMap(36,16,2,"BankAccountNumber"),
                    new SsotReceiverMap(37,19,2,"SenderSourceOfFund"),
                    new SsotReceiverMap(38,20,2,"PurposeOfRemittance"),
                    new SsotReceiverMap(39,21,2,"PaymentMode"),
                    new SsotReceiverMap(40,22,2,"ReceiverAddress"),
                    new SsotReceiverMap(41,23,2,"SenderBeneficiaryRelationship"),
                    new SsotReceiverMap(42,24,2,"ReceiverCity"),
                    new SsotReceiverMap(43,25,2,"ReceiverIdNumber"),
                    new SsotReceiverMap(44,26,2,"ReceiverIdType"),
                    new SsotReceiverMap(45,27,2,"SenderState"),
                    new SsotReceiverMap(46,1,3,"remitGivenName"), 
                    new SsotReceiverMap(47,2,3,"remitSurname"), 
                    new SsotReceiverMap(48,4,3,"nationality"),
                    new SsotReceiverMap(49,5,3,"remitCaType"), 
                    new SsotReceiverMap(50,6,3,"remitCountryCode"),
                    new SsotReceiverMap(51,7,3,"remitCaNo"), 
                    new SsotReceiverMap(52,8,3,"settleCurrency"),
                    new SsotReceiverMap(53,9,3,"remitAddress"),
                    new SsotReceiverMap(54,13,3,"payeeGivenName"),
                    new SsotReceiverMap(55,14,3,"payeeSurname"),
                    new SsotReceiverMap(56,15,3,"payeePhone"),
                    new SsotReceiverMap(57,16,3,"payeeAccountNo"),
                    new SsotReceiverMap(58,17,3,"transCurrency"),
                    new SsotReceiverMap(59,18,3,"merTransAmount"),
                    new SsotReceiverMap(60,19,3,"sourceOfFunds"),
                    new SsotReceiverMap(61,20,3,"remitPurpose"),
                    new SsotReceiverMap(62,25,3,"payeeCaNo"),
                    new SsotReceiverMap(63,26,3,"payeeCaType"),
                    new SsotReceiverMap(64,28,3,"payeeBirthDate"),
                    new SsotReceiverMap(65,29,3,"payeeBankName"),
                    new SsotReceiverMap(66,30,3,"payeeBranchName"),
                    new SsotReceiverMap(67,31,3,"remitAccountNo"),
                    new SsotReceiverMap(68,33,2,"ReceiverNationality")
                );
                // .deleteAll() clears out any data that was in the database
                repository.deleteAll();

                // .saveAll() adds new data to the database
                repository.saveAll(ssotReceiverMap);
            };

            
            
        }
}