package com.nguyenpham.oganicshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nguyenpham.oganicshop.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private long id;
    private String name;
    private String url;
    private String publishYear;
    private String authors;
    private MultipartFile image1;
    private MultipartFile image2;
    private String description;
    private Double price;
    private Double discount;
    private Double finalPrice;
    private int amount;
    private long categoryId;
}
