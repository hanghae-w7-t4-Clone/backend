package com.backend.hanghaew7t4clone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HanghaeW7T4CloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(HanghaeW7T4CloneApplication.class, args);
    }

}
