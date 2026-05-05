package com.effectiveapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // Required for the "Zombie Hunter" task to run automatically
public class EffectiveApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(EffectiveApiApplication.class, args);
    }
}