package com.example.springboot.ssot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.*;

@Configuration
public class SsotConfig {
    @Bean
    CommandLineRunner ssotCommandLineRunner(
        SsotRepository repository) {
            return args ->{
                List<Ssot> ssots = new ArrayList<Ssot>();
                Collections.addAll(ssots, 
                    new Ssot(1,"sender_first_name"), 
                    new Ssot(2,"sender_last_name"), 
                    new Ssot(3,"sender_date_of_birth"), 
                    new Ssot(4,"sender_nationality"),
                    new Ssot(5,"sender_identity_type"), 
                    new Ssot(6,"sender_country"),
                    new Ssot(7,"sender_id_number"), 
                    new Ssot(8,"sender_currency"),
                    new Ssot(9,"sender_address"),
                    new Ssot(10,"sender_city"),
                    new Ssot(11,"sender_country_code"),
                    new Ssot(12,"receiver_country"),
                    new Ssot(13,"receiver_first_name"),
                    new Ssot(14,"receiver_last_name"),
                    new Ssot(15,"receiver_mobile_number"),
                    new Ssot(16,"receiver_account_number"),
                    new Ssot(17,"receiver_currency"),
                    new Ssot(18,"amount"),
                    new Ssot(19,"source_of_funds"),
                    new Ssot(20,"remittance_purpose"),
                    new Ssot(21,"payment_mode"),
                    new Ssot(22,"receiver_address"),
                    new Ssot(23,"relationship"),
                    new Ssot(24,"receiver_city"),
                    new Ssot(25,"receiver_id_number"),
                    new Ssot(26,"receiver_id_type"),
                    new Ssot(27,"sender_state"),
                    new Ssot(28,"receiver_date_of_birth"),
                    new Ssot(29,"receiver_bank"),
                    new Ssot(30,"receiver_bank_branch"),
                    new Ssot(31,"sender_bank_no"),
                    new Ssot(32,"sender_id_country"),
                    new Ssot(33,"receiver_nationality")
                );
                
                // .deleteAll() clears out any data that was in the database
                repository.deleteAll();

                // .saveAll() adds new data to the database
                repository.saveAll(ssots);
            };

            
            
        }
}
