package com.nguyenpham.oganicshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ProductResponse {

    private Long id;
    private String productName;
    private String productUrl;
    private String mainImage;
    private List<String> images;
    private String baseDescription;
    private String detailDescription;
    private int price;
    private int discount;
    private int finalPrice;
    private int rating;
    private int numberOfReviews;
    private int amount;
    private int amountSold;
    private String status;
    private String categoryName;
    private String supplierName;
    private List<ReviewResponse> reviews;
}
