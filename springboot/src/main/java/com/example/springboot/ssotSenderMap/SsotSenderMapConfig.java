package com.example.springboot.ssotSenderMap;
// package com.example.springboot.config;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import java.util.*;
// import com.example.springboot.model.SsotSenderMap;
// import com.example.springboot.repository.SsotSenderMapRepository;

// @Configuration
// public class SsotSenderMapConfig {
//     @Bean
//     CommandLineRunner ssotSenderMapCommandLineRunner(
//         SsotSenderMapRepository repository) {
//             return args ->{
//                 List<SsotSenderMap> ssotSenderMap = new ArrayList<SsotSenderMap>();
//                 Collections.addAll(ssotSenderMap, 
//                     new SsotSenderMap(1,1,"test_sender_first_name",1), 
//                     new SsotSenderMap(2,2,"test_sender_last_name",1), 
//                     new SsotSenderMap(3,3,"test_sender_date_of_birth",1), 
//                     new SsotSenderMap(4,4,"test_sender_nationality",1),
//                     new SsotSenderMap(5,5,"test_sender_identity_type",1), 
//                     new SsotSenderMap(6,6,"test_sender_country",1),
//                     new SsotSenderMap(7,7,"test_sender_id_number",1), 
//                     new SsotSenderMap(8,8,"test_sender_currency",1),
//                     new SsotSenderMap(9,9,"test_sender_address",1),
//                     new SsotSenderMap(10,10,"test_sender_city",	1),
//                     new SsotSenderMap(11,11,"test_sender_country_code",1),
//                     new SsotSenderMap(12,12,"test_receiver_country",1),
//                     new SsotSenderMap(13,13,"test_receiver_first_name",1),
//                     new SsotSenderMap(14,14,"test_receiver_last_name",1),
//                     new SsotSenderMap(15,15,"test_receiver_mobile_number",1),
//                     new SsotSenderMap(16,16,"test_receiver_account_number",1),
//                     new SsotSenderMap(17,17,"test_receiver_currency",1),
//                     new SsotSenderMap(18,18,"test_amount",1),
//                     new SsotSenderMap(19,19,"test_source_of_funds",1),
//                     new SsotSenderMap(20,20,"test_remittance_purpose",1),
//                     new SsotSenderMap(21,21,"test_payment_mode",1),
//                     new SsotSenderMap(22,22,"test_receiver_address",1),
//                     new SsotSenderMap(23,23,"test_relationship",1),
//                     new SsotSenderMap(24,24,"test_receiver_city",1),
//                     new SsotSenderMap(25,25,"test_receiver_id_number",1),
//                     new SsotSenderMap(26,26,"test_receiver_id_type",1),
//                     new SsotSenderMap(27,27,"test_sender_state",1),
//                     new SsotSenderMap(28,28,"test_receiver_date_of_birth",1),
//                     new SsotSenderMap(29,29,"test_receiver_bank",1),
//                     new SsotSenderMap(30,30,"test_receiver_bank_branch",1),
//                     new SsotSenderMap(31,31,"test_sender_bank_no",1),
//                     new SsotSenderMap(32,32,"test_sender_id_country",1),
//                     new SsotSenderMap(33,33,"test_receiver_nationality",1)
//                 );
                
//                 // .deleteAll() clears out any data that was in the database
//                 repository.deleteAll();

//                 // .saveAll() adds new data to the database
//                 repository.saveAll(ssotSenderMap);
//             };

            
            
//         }
// }