package com.website.Shyne_jewelry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ShyneJewelryApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShyneJewelryApplication.class, args);

    }
}
