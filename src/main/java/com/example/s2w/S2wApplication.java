package com.example.s2w;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class S2wApplication {

    public static void main(String[] args) {
        SpringApplication.run(S2wApplication.class, args);
    }

}
