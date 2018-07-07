package io.apicollab.seeder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Slf4j
public class SeederApplication {

    @Autowired
    private DataSeeder dataSeeder;

    public static void main(String[] args) {
        SpringApplication.run(SeederApplication.class, args);
    }

    @Bean
    @Profile("!test")
    public CommandLineRunner seed() {
        return args -> {
            dataSeeder.seed();
            ;
        };
    }
}
