package com.nguyenpham.oganicshop;

import com.nguyenpham.oganicshop.entity.OrderDetail;
import com.nguyenpham.oganicshop.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import javax.transaction.Transactional;
import java.util.Set;

@SpringBootApplication(scanBasePackages = "com.nguyenpham.oganicshop")
@EnableCaching
public class OganicshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(OganicshopApplication.class, args);
    }

}
