package com.nguyenpham.oganicshop.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nguyenpham.oganicshop.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    private String name;
    private String url;
    private String image;
    private String size;
    private String color;
    private String baseDescription;
    private String detailDescription;
    private int price;
    private int discount;
    private int finalPrice;
    private int amount;
    private long categoryId;
    private long supplierId;

    public Product dtoToEntity() {
        Product product = new Product();
        product.setName(this.getName());
        product.setUrl(this.getUrl());
        product.setImage(this.getImage());
        product.setSize(this.getSize());
        product.setColor(this.getColor());
        product.setBaseDescription(this.getBaseDescription());
        product.setDetailDescription(this.getDetailDescription());
        product.setPrice(this.getPrice());
        product.setDiscount(this.getDiscount());
        product.setFinalPrice(this.getFinalPrice());
        product.setAmount(this.getAmount());

        return product;
    }
}
