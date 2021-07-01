package com.nguyenpham.oganicshop;

import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;


@SpringBootApplication(scanBasePackages = "com.nguyenpham.oganicshop")
@EnableCaching
public class OganicshopApplication{

    public static void main(String[] args) throws ParseException {
        SpringApplication.run(OganicshopApplication.class, args);
    }
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        List<Product> products = productRepository.findAll();
//        products.forEach(product -> {
//            if ( (product.getId() > 5000) & (product.getId() <= 6000)) {
//                System.out.println(product.getId());
//                product.setPrice(950.0);
//                product.setFinalPrice(product.getPrice() - (product.getPrice() * product.getDiscount()));
//            }
//        });
//
//        productRepository.saveAll(products);
//    }

}
