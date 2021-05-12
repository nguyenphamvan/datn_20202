package com.nguyenpham.oganicshop;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication(scanBasePackages = "com.nguyenpham.oganicshop")
@EnableCaching
public class OganicshopApplication{

    public static void main(String[] args) {
        SpringApplication.run(OganicshopApplication.class, args);
    }

}
