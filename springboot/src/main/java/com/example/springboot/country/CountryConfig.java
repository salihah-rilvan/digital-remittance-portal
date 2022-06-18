package com.example.springboot.country;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.*;

@Configuration
public class CountryConfig {
    @Bean
    CommandLineRunner CountryCommandLineRunner(
        CountryRepository repository) {
            return args ->{
                List<Country> countries = new ArrayList<Country>();
                Collections.addAll(countries, 
                    new Country("China"),
                    new Country("Vietnam"),
                    new Country("Singapore")
                );
                // .deleteAll() clears out any data that was in the database
                repository.deleteAll();

                // .saveAll() adds new data to the database
                repository.saveAll(countries);
            };

            
            
        }
}