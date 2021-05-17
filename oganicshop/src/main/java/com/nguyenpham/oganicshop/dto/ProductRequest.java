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
    private MultipartFile[] images;
    private String baseDescription;
    private String detailDescription;
    private int price;
    private int discount;
    private int finalPrice;
    private int amount;
    private long categoryId;
    private long parentCategoryId;
    private long supplierId;
}
