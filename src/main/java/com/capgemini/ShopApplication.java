package com.capgemini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ShopApplication {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "mysql");

        SpringApplication.run(ShopApplication.class, args);
    }
}
