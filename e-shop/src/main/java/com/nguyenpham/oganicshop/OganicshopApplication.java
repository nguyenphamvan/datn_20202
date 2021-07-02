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
import java.time.temporal.ChronoUnit;
import java.util.*;


@SpringBootApplication(scanBasePackages = "com.nguyenpham.oganicshop")
@EnableCaching
public class OganicshopApplication implements CommandLineRunner{

    public static void main(String[] args) throws ParseException {
        SpringApplication.run(OganicshopApplication.class, args);
    }
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        String date = "2021-07-03T05:35:30Z";
        System.out.println(date);
        date = convertDatetime2(date, null);
        System.out.println(date);
    }

    public static String convertDatetime2(String inputDate, String pattern) {
        Instant instant = Instant.parse(inputDate);
        return instant.plus(7, ChronoUnit.HOURS).toString();
    }

}
