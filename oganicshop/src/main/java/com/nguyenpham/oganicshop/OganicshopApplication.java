package com.nguyenpham.oganicshop;

import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.List;
import java.util.Locale;


@SpringBootApplication(scanBasePackages = "com.nguyenpham.oganicshop")
@EnableCaching
public class OganicshopApplication{

    public static void main(String[] args) {
        SpringApplication.run(OganicshopApplication.class, args);
    }

}
