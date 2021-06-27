package com.nguyenpham.oganicshop;

import com.nguyenpham.oganicshop.entity.Product;
import com.nguyenpham.oganicshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;


@SpringBootApplication(scanBasePackages = "com.nguyenpham.oganicshop")
@EnableCaching
public class OganicshopApplication{

    public static void main(String[] args) {
        SpringApplication.run(OganicshopApplication.class, args);
    }
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        List<Product> products = productRepository.findAll();
//        products.forEach(product -> {
//            String[] proUrl = product.getOriginalTitle().replace("/","").replace("\"", "").replace("#", "")
//                    .replace("*","").replace("(","").replace(")","")
//                    .replace(",","").replace("'","").replace(":","")
//                    .replace(".","")
//                    .trim().toLowerCase().split(" ");
//            String newUrl = String.join("_", proUrl) + "_" + product.getBookId();
//            product.setProductUrl(newUrl);
//        });
//
//        productRepository.saveAll(products);
//    }
}
