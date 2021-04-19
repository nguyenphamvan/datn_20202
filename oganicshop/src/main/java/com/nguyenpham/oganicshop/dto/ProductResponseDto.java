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
public class ProductResponseDto {

    private Long id;
    private String productName;
    private String productUrl;
    private String image;
    private String size;
    private String color;
    private String baseDescription;
    private String detailDescription;
    private int price;
    private int discount;
    private int finalPrice;
    private int rating;
    private int numberOfReviews;
    private int amount;
    private String status;
    private String categoryName;
    private String supplierName;
    private List<ResponseReviewDto> reviews;
}
