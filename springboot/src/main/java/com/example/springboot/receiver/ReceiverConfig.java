package com.example.springboot.receiver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.*;

@Configuration
public class ReceiverConfig {
    @Bean
    CommandLineRunner receiverCommandLineRunner(
        ReceiverRepository repository) {
            return args -> {
                List<Receiver> receivers = new ArrayList<Receiver>();
                Collections.addAll(receivers, 
                    new Receiver(1,"EverywhereRemit",1,"https://prelive.paywho.com/api/smu_send_transaction"),
                    new Receiver(2,"FinanceNow",1,"https://prelive.paywho.com/api/smu_send_transaction"),
                    new Receiver(3,"PaymentGo",1,"https://prelive.paywho.com/api/smu_send_transaction")
                    );

                // Deletes any data in table on startup
                repository.deleteAll();

                // Repopulates data in list
                repository.saveAll(receivers);

            };
        }
}
